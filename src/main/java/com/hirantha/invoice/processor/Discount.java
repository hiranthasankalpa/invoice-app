package com.hirantha.invoice.processor;

import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Discount implements Comparable<Discount> {

  private UserType type;

  private double discountPercentage;

  private int priority;

  public double getDiscount(UserDto user, DiscountChain chain, int index) {
    if (this.getType().equals(user.getType())) {
      return getDiscountPercentage();
    }
    if (chain != null) {
      return chain.getDiscount(user, index + 1);
    }
    return 0;
  }

  @Override
  public int compareTo(Discount compareDiscount) {
    return this.priority - compareDiscount.getPriority();
  }

  @Override
  public String toString() {
    return "[ type=" + type + ", discountPercentage=" + discountPercentage + ", priority="
        + priority + "]";
  }

}
