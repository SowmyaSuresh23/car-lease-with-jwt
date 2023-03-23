package com.sowmya.carleaseplatform.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailsNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6712916283983119279L;
  private String errorMessage;
}
