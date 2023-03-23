package com.sowmya.customerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAlreadyExistsException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6453713673798090634L;
  private String errorMessage;
}
