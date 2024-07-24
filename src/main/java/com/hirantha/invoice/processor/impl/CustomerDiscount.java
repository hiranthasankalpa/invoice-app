package com.hirantha.invoice.processor.impl;

import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import com.hirantha.invoice.processor.Discount;
import com.hirantha.invoice.processor.DiscountChain;
import java.time.LocalDate;

public class CustomerDiscount extends Discount {

  int registeredYears;

  public CustomerDiscount(int discountPercentage, int priority, int registeredYears) {
    super(UserType.CUSTOMER, discountPercentage, priority);
    this.registeredYears = registeredYears;
  }

  @Override
  public double getDiscount(UserDto user, DiscountChain chain, int index) {
    if (UserType.CUSTOMER.equals(user.getType())
        && user.getRegisteredDate().isBefore(LocalDate.now().minusYears(2))) {
      return getDiscountPercentage();
    }
    return 0;
  }
}
