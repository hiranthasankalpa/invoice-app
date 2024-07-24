package com.hirantha.invoice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.hirantha.invoice.data.CommonTestData;
import com.hirantha.invoice.dto.InvoiceDto;
import com.hirantha.invoice.dto.ItemDto;
import com.hirantha.invoice.processor.DiscountChain;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceTest {

  @Mock
  private DiscountChain discountChain;

  @Autowired
  private InvoiceService invoiceService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateInvoice_withOnlyGrocery_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getAffiliate());
    invoice.setItems(CommonTestData.getGroceryItems());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("210.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("210.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withOnlyNonGroceryBelow100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getAffiliate());
    invoice.setItems(CommonTestData.getNonGroceryItemsBelow100());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("30.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("30.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withOnlyNonGroceryAbove100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getAffiliate());
    invoice.setItems(CommonTestData.getNonGroceryItemsAbove100());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("340.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("310.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryBelow100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getAffiliate());

    List<ItemDto> items = CommonTestData.getGroceryItems();
    items.addAll(CommonTestData.getNonGroceryItemsBelow100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("240.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("240.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getAffiliate());

    List<ItemDto> items = CommonTestData.getGroceryItems();
    items.addAll(CommonTestData.getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("520.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forEmployeeUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getEmployee());

    List<ItemDto> items = CommonTestData.getGroceryItems();
    items.addAll(CommonTestData.getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(30.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("460.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forNewCustomer() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getNewCustomer());

    List<ItemDto> items = CommonTestData.getGroceryItems();
    items.addAll(CommonTestData.getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(0.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("550.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forOldCustomer() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(CommonTestData.getOldCustomer());

    List<ItemDto> items = CommonTestData.getGroceryItems();
    items.addAll(CommonTestData.getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(5.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("535.00"), generatedInvoice.getNetAmount());
  }

}
