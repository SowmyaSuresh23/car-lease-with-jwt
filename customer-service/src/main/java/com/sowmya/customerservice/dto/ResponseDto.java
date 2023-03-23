package com.sowmya.customerservice.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String responseMessage;

  private String responseCode;
}
