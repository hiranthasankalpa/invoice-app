package com.hirantha.invoice.controller;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirantha.invoice.data.CommonTestData;
import com.hirantha.invoice.dto.InvoiceDto;
import com.hirantha.invoice.dto.ItemDto;
import com.hirantha.invoice.dto.UserDto;
import com.hirantha.invoice.enums.UserType;
import com.hirantha.invoice.service.InvoiceService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  InvoiceService invoiceService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldReturnOk() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());
    request.setItems(CommonTestData.getNonGroceryItemsAbove100());

    InvoiceDto expectedResponse = new InvoiceDto();
    expectedResponse.setUser(CommonTestData.getAffiliate());
    expectedResponse.setItems(CommonTestData.getNonGroceryItemsAbove100());
    expectedResponse.setSubTotal(new BigDecimal("340.00"));
    expectedResponse.setNetAmount(new BigDecimal("310.00"));

    when(invoiceService.generateInvoice(request)).thenReturn(expectedResponse);

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertEquals(response, objectMapper.writeValueAsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorUserName() throws Exception {
    InvoiceDto request = new InvoiceDto();

    UserDto user = new UserDto();
    user.setType(UserType.EMPLOYEE);
    user.setRegisteredDate(LocalDate.now());
    request.setUser(user);

    request.setItems(CommonTestData.getNonGroceryItemsAbove100());

    String expectedResponse = "User name cannot be blank";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorUserRegisteredDate() throws Exception {
    InvoiceDto request = new InvoiceDto();

    UserDto user = new UserDto();
    user.setName("User");
    user.setType(UserType.EMPLOYEE);
    request.setUser(user);

    request.setItems(CommonTestData.getNonGroceryItemsAbove100());

    String expectedResponse = "User Registered Date must be present";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorUserType() throws Exception {
    InvoiceDto request = new InvoiceDto();

    UserDto user = new UserDto();
    user.setName("User");
    user.setRegisteredDate(LocalDate.now());
    request.setUser(user);

    request.setItems(CommonTestData.getNonGroceryItemsAbove100());

    String expectedResponse = "User Type must be present";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemName() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    ItemDto item = new ItemDto();
    item.setPrice(new BigDecimal("30.0"));
    item.setGrocery(false);

    request.setItems(Arrays.asList(item));

    String expectedResponse = "Item name cannot be blank";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemPriceNull() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    ItemDto item = new ItemDto();
    item.setName("Item");
    item.setGrocery(false);

    request.setItems(Arrays.asList(item));

    String expectedResponse = "Item price must be present";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemPriceZero() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    ItemDto item = new ItemDto();
    item.setName("Item");
    item.setPrice(new BigDecimal("0.0"));
    item.setGrocery(false);

    request.setItems(Arrays.asList(item));

    String expectedResponse = "Item price must be valid";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemPriceNegative() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    ItemDto item = new ItemDto();
    item.setName("Item");
    item.setPrice(new BigDecimal("-10.0"));
    item.setGrocery(false);

    request.setItems(Arrays.asList(item));

    String expectedResponse = "Item price must be valid";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorUserNull() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setItems(CommonTestData.getNonGroceryItemsAbove100());

    String expectedResponse = "User must be present";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemsNull() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    String expectedResponse = "There should be at least one item per invoice";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

  @Test
  void shouldReturnValidationErrorItemsEmpty() throws Exception {
    InvoiceDto request = new InvoiceDto();
    request.setUser(CommonTestData.getAffiliate());

    request.setItems(new ArrayList<>());

    String expectedResponse = "There should be at least one item per invoice";

    String response = this.mockMvc.perform(post("/invoices/discounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertThat(response, containsString(expectedResponse));
  }

}
