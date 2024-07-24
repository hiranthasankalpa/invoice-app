package com.hirantha.invoice.service.impl;

import com.hirantha.invoice.dto.InvoiceDto;
import com.hirantha.invoice.dto.ItemDto;
import com.hirantha.invoice.processor.DiscountChain;
import com.hirantha.invoice.service.InvoiceService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

  private final DiscountChain discountChain;

  @Override
  public InvoiceDto generateInvoice(InvoiceDto invoice) {

    BigDecimal nonGroceryTotal = invoice.getItems().stream()
        .filter(item -> !item.isGrocery())
        .map(ItemDto::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal groceryTotal = invoice.getItems().stream()
        .filter(ItemDto::isGrocery)
        .map(ItemDto::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    invoice.setSubTotal(nonGroceryTotal.add(groceryTotal));

    BigDecimal discountApplicableHundredth = nonGroceryTotal.divide(new BigDecimal(100), 0,
        RoundingMode.DOWN);

    if (discountApplicableHundredth.compareTo(new BigDecimal(0)) > 0) {
      double discountPercentage = discountChain.getDiscount(invoice.getUser());
      BigDecimal discountAmount = discountApplicableHundredth.multiply(
          new BigDecimal(discountPercentage));
      nonGroceryTotal = nonGroceryTotal.subtract(discountAmount);
    }

    invoice.setNetAmount(nonGroceryTotal.add(groceryTotal));

    return invoice;
  }
}
