package com.hirantha.invoice.config;

import com.hirantha.invoice.processor.DiscountChain;
import com.hirantha.invoice.processor.impl.AffiliateDiscount;
import com.hirantha.invoice.processor.impl.CustomerDiscount;
import com.hirantha.invoice.processor.impl.DiscountChainImpl;
import com.hirantha.invoice.processor.impl.EmployeeDiscount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DiscountChainConfig {

  private final DiscountPropertyConfig discountPropertyConfig;

  @Bean
  public DiscountChain getDiscountChain() {
    DiscountChain discountChain = new DiscountChainImpl();

    discountChain.addDiscount(
        new AffiliateDiscount(discountPropertyConfig.getAffiliate().get("percentage"),
            discountPropertyConfig.getAffiliate().get("priority")));
    discountChain.addDiscount(
        new EmployeeDiscount(discountPropertyConfig.getEmployee().get("percentage"),
            discountPropertyConfig.getEmployee().get("priority")));
    discountChain.addDiscount(
        new CustomerDiscount(discountPropertyConfig.getCustomer().get("percentage"),
            discountPropertyConfig.getCustomer().get("priority"),
            discountPropertyConfig.getCustomer().get("registeredYears")));

    discountChain.prioritize();

    return discountChain;
  }

}
