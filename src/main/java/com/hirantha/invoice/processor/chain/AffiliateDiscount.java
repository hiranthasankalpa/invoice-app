package com.hirantha.invoice.processor.chain;

import com.hirantha.invoice.enums.UserType;
import com.hirantha.invoice.processor.Discount;

public class AffiliateDiscount extends Discount {

  public AffiliateDiscount(int discountPercentage, int priority) {
    super(UserType.AFFILIATE, discountPercentage, priority);
  }
}
