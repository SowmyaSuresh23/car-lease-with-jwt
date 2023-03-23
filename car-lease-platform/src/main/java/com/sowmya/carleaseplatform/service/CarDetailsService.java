package com.sowmya.carleaseplatform.service;

import com.sowmya.carleaseplatform.dto.CarDetailsRequest;
import com.sowmya.carleaseplatform.dto.CarDetailsResponse;
import com.sowmya.carleaseplatform.dto.ResponseDto;

/**
 * @author Sowmya Suresh
 *
 */
public interface CarDetailsService {

  public ResponseDto saveCarDetails(CarDetailsRequest newCar);

  public ResponseDto deleteCar(String carNumber);

  public CarDetailsResponse findCarByCarNumber(String carNumber);

  public ResponseDto updateCarDetailsByName(CarDetailsRequest request);
}
