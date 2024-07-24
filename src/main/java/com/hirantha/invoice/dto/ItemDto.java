package com.hirantha.invoice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ItemDto {

  @NotBlank(message = "Item name cannot be blank")
  private String name;

  @DecimalMin(value = "0.0", inclusive = false, message = "Item price must be valid")
  @NotNull(message = "Item price must be present")
  private BigDecimal price;

  private boolean grocery;

}
