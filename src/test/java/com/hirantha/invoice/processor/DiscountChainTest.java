package com.hirantha.invoice.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import java.time.LocalDate;
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
    UserDto user = getEmployee();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(30.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forAffiliate() {
    UserDto user = getAffiliate();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(10.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forOldCustomer() {
    UserDto user = getOldCustomer();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(5.0, discountPercentage);
  }

  @Test
  public void testGetDiscount_forNewCustomer() {
    UserDto user = getNewCustomer();
    double discountPercentage = discountChain.getDiscount(user);

    assertEquals(0.0, discountPercentage);
  }

  private UserDto getEmployee() {
    UserDto user = new UserDto();
    user.setName("Employee");
    user.setType(UserType.EMPLOYEE);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  private UserDto getAffiliate() {
    UserDto user = new UserDto();
    user.setName("Affiliate");
    user.setType(UserType.AFFILIATE);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  private UserDto getNewCustomer() {
    UserDto user = new UserDto();
    user.setName("New Customer");
    user.setType(UserType.CUSTOMER);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  private UserDto getOldCustomer() {
    UserDto user = new UserDto();
    user.setName("OLD Customer");
    user.setType(UserType.CUSTOMER);
    user.setRegisteredDate(LocalDate.now().minusYears(3));

    return user;
  }

}
