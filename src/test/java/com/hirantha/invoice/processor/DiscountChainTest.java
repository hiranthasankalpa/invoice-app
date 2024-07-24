package com.hirantha.invoice.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hirantha.invoice.data.CommonTestData;
import com.hirantha.invoice.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountChainTest {

  @Autowired
  private DiscountChain discountChain;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetDiscount_forEmployee() {
    UserDto user = CommonTestData.getEmployee();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(30.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forAffiliate() {
    UserDto user = CommonTestData.getAffiliate();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(10.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forOldCustomer() {
    UserDto user = CommonTestData.getOldCustomer();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(5.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forNewCustomer() {
    UserDto user = CommonTestData.getNewCustomer();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(0.0, discountPercentage);
  }

}
