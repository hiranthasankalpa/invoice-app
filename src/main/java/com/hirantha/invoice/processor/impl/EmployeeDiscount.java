package com.hirantha.invoice.processor.impl;

import com.hirantha.invoice.enums.UserType;
import com.hirantha.invoice.processor.Discount;

public class EmployeeDiscount extends Discount {

  public EmployeeDiscount(int discountPercentage, int priority) {
    super(UserType.EMPLOYEE, discountPercentage, priority);
  }

}
