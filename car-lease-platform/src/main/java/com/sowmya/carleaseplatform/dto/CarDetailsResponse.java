package com.sowmya.carleaseplatform.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsResponse extends ResponseDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String carNumber;

  private String make;

  private String model;

  private String version;

  private Integer noOfDoors;

  private Double co2Emission;

  private Double grossPrice;

  private Double netPrice;

  private Float mileage;

  private Float interestRate;

}
