package com.hirantha.invoice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.hirantha.invoice.dto.InvoiceDto;
import com.hirantha.invoice.dto.ItemDto;
import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import com.hirantha.invoice.processor.DiscountChain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceServiceTest {

  @Mock
  private DiscountChain discountChain;

  @Autowired
  private InvoiceService invoiceService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateInvoice_withOnlyGrocery_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getAffiliate());
    invoice.setItems(getGroceryItems());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("210.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("210.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withOnlyNonGroceryBelow100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getAffiliate());
    invoice.setItems(getNonGroceryItemsBelow100());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("30.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("30.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withOnlyNonGroceryAbove100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getAffiliate());
    invoice.setItems(getNonGroceryItemsAbove100());

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("340.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("310.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryBelow100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getAffiliate());

    List<ItemDto> items = getGroceryItems();
    items.addAll(getNonGroceryItemsBelow100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("240.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("240.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forAffiliateUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getAffiliate());

    List<ItemDto> items = getGroceryItems();
    items.addAll(getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(10.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("520.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forEmployeeUser() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getEmployee());

    List<ItemDto> items = getGroceryItems();
    items.addAll(getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(30.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("460.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forNewCustomer() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getNewCustomer());

    List<ItemDto> items = getGroceryItems();
    items.addAll(getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(0.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("550.00"), generatedInvoice.getNetAmount());
  }

  @Test
  public void testGenerateInvoice_withGroceryAndNonGroceryAbove100_forOldCustomer() {
    InvoiceDto invoice = new InvoiceDto();
    invoice.setUser(getOldCustomer());

    List<ItemDto> items = getGroceryItems();
    items.addAll(getNonGroceryItemsAbove100());
    invoice.setItems(items);

    when(discountChain.getDiscount(invoice.getUser())).thenReturn(5.0);

    InvoiceDto generatedInvoice = invoiceService.generateInvoice(invoice);

    assertEquals(new BigDecimal("550.00"), generatedInvoice.getSubTotal());
    assertEquals(new BigDecimal("535.00"), generatedInvoice.getNetAmount());
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

  private List<ItemDto> getGroceryItems() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Grocery 1");
    item1.setPrice(new BigDecimal("80.00"));
    item1.setGrocery(true);

    ItemDto item2 = new ItemDto();
    item1.setName("Grocery 2");
    item2.setPrice(new BigDecimal("130.00"));
    item2.setGrocery(true);

    items.add(item1);
    items.add(item2);

    return items;
  }

  private List<ItemDto> getNonGroceryItemsBelow100() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Non Grocery Small 1");
    item1.setPrice(new BigDecimal("10.00"));
    item1.setGrocery(false);

    ItemDto item2 = new ItemDto();
    item1.setName("Non Grocery Small 2");
    item2.setPrice(new BigDecimal("20.00"));
    item2.setGrocery(false);

    items.add(item1);
    items.add(item2);

    return items;
  }

  private List<ItemDto> getNonGroceryItemsAbove100() {
    List<ItemDto> items = new ArrayList<>();

    ItemDto item1 = new ItemDto();
    item1.setName("Non Grocery Big 1");
    item1.setPrice(new BigDecimal("110.00"));
    item1.setGrocery(false);

    ItemDto item2 = new ItemDto();
    item1.setName("Non Grocery Big 2");
    item2.setPrice(new BigDecimal("230.00"));
    item2.setGrocery(false);

    items.add(item1);
    items.add(item2);

    return items;
  }

}
