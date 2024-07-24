package com.hirantha.invoice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class InvoiceDto {

  @Valid
  @NotNull(message = "User must be present")
  private UserDto user;

  @Valid
  @NotEmpty(message = "There should be at least one item per invoice")
  private List<ItemDto> items;

  private BigDecimal subTotal;

  private BigDecimal netAmount;

}
