package com.hirantha.invoice.dto;

import com.hirantha.invoice.enums.UserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

  @NotEmpty(message = "User name cannot be blank")
  private String name;

  @NotNull(message = "User Type must be present")
  private UserType type;

  @NotNull(message = "User Registered Date must be present")
  private LocalDate registeredDate;

}
