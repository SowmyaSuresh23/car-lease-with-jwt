package com.sowmya.carleaseplatform.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarAlreadyExistsException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -1680708362359160640L;
  private String errorMessage;
}
