package com.sowmya.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetailsRequest {

  @NotNull(message = "Customer name is mandatory")
  @Size(max = 50, message = "Customer name cannot have more than 50 characters")
  private String customerName;

  @NotNull(message = "Street is mandatory")
  private String street;

  @NotNull(message = "House number is mandatory")
  private String houseNumber;

  @NotNull(message = "Zip code is mandatory")
  @Size(min = 6, max = 7, message = "Please provide a valid zip code")
  @Pattern(regexp = "^[0-9]{4} ?[A-Z]{2}$", message = "Please provide a valid zip code")
  private String zipCode;

  @NotNull(message = "Place is mandatory")
  private String place;

  @NotNull(message = "Email is mandatory")
  @Email(message = "Please enter a valid email address")
  private String email;

  @NotNull(message = "Phone number is mandatory")
  @Pattern(regexp = "^\\+[1-9]{1}[0-9]{10,14}$", message = "Please provide a valid phone number.")
  private String phoneNumber;

  @NotNull(message = "User name is mandatory")
  @Size(max = 50, message = "User name cannot have more than 50 characters")
  private String userName;
}
