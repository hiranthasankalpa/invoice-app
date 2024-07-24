package com.hirantha.invoice.processor;

import com.hirantha.invoice.dto.UserDto;

public interface DiscountChain {

  void addDiscount(Discount discount);

  double getDiscount(UserDto user);

  double getDiscount(UserDto user, int index);

  void prioritize();

}
