package com.hirantha.invoice.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "discounts")
public class DiscountPropertyConfig {

  private Map<String, Integer> employee;

  private Map<String, Integer> affiliate;

  private Map<String, Integer> customer;

}
