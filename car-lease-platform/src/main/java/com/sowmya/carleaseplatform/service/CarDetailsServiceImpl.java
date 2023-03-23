package com.sowmya.carleaseplatform.service;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sowmya.carleaseplatform.constants.CarLeaseServiceConstants;
import com.sowmya.carleaseplatform.dto.CarDetailsRequest;
import com.sowmya.carleaseplatform.dto.CarDetailsResponse;
import com.sowmya.carleaseplatform.dto.ResponseDto;
import com.sowmya.carleaseplatform.entity.CarDetailsEntity;
import com.sowmya.carleaseplatform.exception.CarAlreadyExistsException;
import com.sowmya.carleaseplatform.exception.CarLeaseException;
import com.sowmya.carleaseplatform.exception.DetailsNotFoundException;
import com.sowmya.carleaseplatform.repository.CarDetailsRepository;
import jakarta.transaction.Transactional;

/**
 * @author Sowmya Suresh
 *
 */


@Service
public class CarDetailsServiceImpl implements CarDetailsService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CarDetailsRepository carDetailsRepository;

  /**
   * This method saves new car details after checking if the car number is unique.
   * 
   * @param newCar has details of a new car
   * @return response code and response message after saving
   * @throws CarLeaseServiceException
   */
  @Transactional
  @Override
  public ResponseDto saveCarDetails(CarDetailsRequest newCar) {
    ResponseDto response = new ResponseDto();
    logger.info("Entering into CarDetailsServiceImpl.saveCarDetails");

    if (carDetailsRepository.findByCarNumberIgnoreCase(newCar.getCarNumber()).isEmpty()) {

      logger.debug("Car is unique");
      CarDetailsEntity newCarEntity = CarDetailsEntity.builder().carNumber(newCar.getCarNumber())
          .make(newCar.getMake()).model(newCar.getModel()).version(newCar.getVersion())
          .noOfDoors(newCar.getNoOfDoors()).co2Emission(newCar.getCo2Emission())
          .grossPrice(newCar.getGrossPrice()).netPrice(newCar.getNetPrice())
          .mileage(newCar.getMileage()).createdUser(newCar.getUserName())
          .createdTimestamp(new Timestamp(System.currentTimeMillis())).build();

      carDetailsRepository.save(newCarEntity);

      logger.debug(CarLeaseServiceConstants.NEW_CAR_SAVE_SUCCESS);
      response.setResponseCode(CarLeaseServiceConstants.SUCCESS_CODE);
      response.setResponseMessage(CarLeaseServiceConstants.NEW_CAR_SAVE_SUCCESS);

    } else {
      logger.error(CarLeaseServiceConstants.DUPLICATE_CAR_NAME);
      throw new CarAlreadyExistsException(CarLeaseServiceConstants.DUPLICATE_CAR_NAME);
    }
    logger.info("Exiting CarDetailsServiceImpl.saveCarDetails");
    return response;
  }



  /**
   * This method deletes car details based on car number. It throws an exception if car number is
   * not found.
   * 
   * @param carNumber
   * @return response code and response message
   * @throws CarLeaseServiceException
   */
  @Transactional
  @Override
  public ResponseDto deleteCar(String carNumber) {
    logger.info("Entering into CarDetailsServiceImpl.deleteCar");
    ResponseDto response = new ResponseDto();

    CarDetailsEntity carEntity = carDetailsRepository.findByCarNumberIgnoreCase(carNumber)
        .orElseThrow(() -> new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND));
    if (carEntity.getCarLeaseDetails() == null) {
      carDetailsRepository.deleteByCarNumberIgnoreCase(carNumber);

      logger.debug(CarLeaseServiceConstants.DELETE_CAR_SUCCESS);
      response.setResponseCode(CarLeaseServiceConstants.SUCCESS_CODE);
      response.setResponseMessage(CarLeaseServiceConstants.DELETE_CAR_SUCCESS);
      return response;
    } else {
      logger.error(CarLeaseServiceConstants.CAR__LEASE_FOUND);
      throw new CarLeaseException(CarLeaseServiceConstants.CAR__LEASE_FOUND);
    }


  }

  /**
   * This method finds car details based on car number and returns appropriate response if car
   * cannot be found.
   * 
   * @param carNumber
   * @return car details
   * @throws CarLeaseServiceException
   */
  @Transactional
  @Override
  public CarDetailsResponse findCarByCarNumber(String carNumber) {
    logger.info("Entering into CarDetailsServiceImpl.findCarByCarNumber");
    CarDetailsResponse response = new CarDetailsResponse();

    CarDetailsEntity carEntity = carDetailsRepository.findByCarNumberIgnoreCase(carNumber)
        .orElseThrow(() -> new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND));


    response = CarDetailsResponse.builder().carNumber(carEntity.getCarNumber())
        .make(carEntity.getMake()).model(carEntity.getModel()).version(carEntity.getVersion())
        .noOfDoors(carEntity.getNoOfDoors()).co2Emission(carEntity.getCo2Emission())
        .grossPrice(carEntity.getGrossPrice()).netPrice(carEntity.getNetPrice())
        .mileage(carEntity.getMileage()).responseCode(CarLeaseServiceConstants.SUCCESS_CODE)
        .responseMessage(CarLeaseServiceConstants.FIND_CAR_SUCCESS).build();

    logger.debug(CarLeaseServiceConstants.FIND_CAR_SUCCESS);

    logger.info("Exiting CarDetailsServiceImpl.findCarByCarNumber");
    return response;
  }

  /**
   * This method checks if a car is available and updates corresponding details.
   * 
   * @param request car details to be updated
   * @return response code and response message
   * @throws CarLeaseServiceException
   */
  @Transactional
  @Override
  public ResponseDto updateCarDetailsByName(CarDetailsRequest request) {
    logger.info("Entering into CarDetailsServiceImpl.updateCarDetailsByName");
    ResponseDto response = new ResponseDto();

    CarDetailsEntity existingCarDetails =
        carDetailsRepository.findByCarNumberIgnoreCase(request.getCarNumber()).orElseThrow(
            () -> new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND));

    existingCarDetails.setMake(request.getMake());
    existingCarDetails.setModel(request.getModel());
    existingCarDetails.setVersion(request.getVersion());
    existingCarDetails.setNoOfDoors(request.getNoOfDoors());
    existingCarDetails.setCo2Emission(request.getCo2Emission());
    existingCarDetails.setGrossPrice(request.getGrossPrice());
    existingCarDetails.setNetPrice(request.getNetPrice());
    existingCarDetails.setMileage(request.getMileage());
    existingCarDetails.setUpdatedUser(request.getUserName());
    existingCarDetails.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

    carDetailsRepository.save(existingCarDetails);


    logger.debug(CarLeaseServiceConstants.UPDATE_CAR_SUCCESS);
    response.setResponseCode(CarLeaseServiceConstants.SUCCESS_CODE);
    response.setResponseMessage(CarLeaseServiceConstants.UPDATE_CAR_SUCCESS);

    logger.info("Exiting CarDetailsServiceImpl.updateCarDetailsByName");
    return response;
  }
}
