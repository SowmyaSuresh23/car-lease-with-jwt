package com.sowmya.carleaseplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Sowmya Suresh
 *
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseDetailsResponse extends ResponseDto {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long leaseId;
  private Float duration;
  private Float interestRate;
  private Double leaseRate;
  private CarDetailsResponse carDetails;
  private CustomerDetailsResponse customerDetails;



}
