package com.hirantha.invoice.data;

import com.hirantha.invoice.dto.ItemDto;
import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommonTestData {

  public static UserDto getEmployee() {
    UserDto user = new UserDto();
    user.setName("Employee");
    user.setType(UserType.EMPLOYEE);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  public static  UserDto getAffiliate() {
    UserDto user = new UserDto();
    user.setName("Affiliate");
    user.setType(UserType.AFFILIATE);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  public static  UserDto getNewCustomer() {
    UserDto user = new UserDto();
    user.setName("New Customer");
    user.setType(UserType.CUSTOMER);
    user.setRegisteredDate(LocalDate.now().minusYears(1));

    return user;
  }

  public static  UserDto getOldCustomer() {
    UserDto user = new UserDto();
    user.setName("OLD Customer");
    user.setType(UserType.CUSTOMER);
    user.setRegisteredDate(LocalDate.now().minusYears(3));

    return user;
  }

  public static  List<ItemDto> getGroceryItems() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Grocery 1");
    item1.setPrice(new BigDecimal("80.00"));
    item1.setGrocery(true);

    ItemDto item2 = new ItemDto();
    item2.setName("Grocery 2");
    item2.setPrice(new BigDecimal("130.00"));
    item2.setGrocery(true);

    items.add(item1);
    items.add(item2);

    return items;
  }

  public static  List<ItemDto> getNonGroceryItemsBelow100() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Non Grocery Small 1");
    item1.setPrice(new BigDecimal("10.00"));
    item1.setGrocery(false);

    ItemDto item2 = new ItemDto();
    item2.setName("Non Grocery Small 2");
    item2.setPrice(new BigDecimal("20.00"));
    item2.setGrocery(false);

    items.add(item1);
    items.add(item2);

    return items;
  }

  public static  List<ItemDto> getNonGroceryItemsAbove100() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Non Grocery Big 1");
    item1.setPrice(new BigDecimal("110.00"));
    item1.setGrocery(false);

    ItemDto item2 = new ItemDto();
    item2.setName("Non Grocery Big 2");
    item2.setPrice(new BigDecimal("230.00"));
    item2.setGrocery(false);

    items.add(item1);
    items.add(item2);

    return items;
  }

}
