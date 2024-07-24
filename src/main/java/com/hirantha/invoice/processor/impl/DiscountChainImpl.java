package com.hirantha.invoice.processor.impl;

import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.processor.Discount;
import com.hirantha.invoice.processor.DiscountChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscountChainImpl implements DiscountChain {

  private final List<Discount> discounts = new ArrayList<>();

  @Override
  public void addDiscount(Discount discount) {
    discounts.add(discount);
  }

  @Override
  public double getDiscount(UserDto user) {
    return getDiscount(user, 0);
  }

  @Override
  public double getDiscount(UserDto user, int index) {
    if (index < discounts.size()) {
      return discounts.get(index).getDiscount(user, this, index);
    }
    return 0;
  }

  @Override
  public void prioritize() {
    Collections.sort(discounts);
  }

}
