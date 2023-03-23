package com.sowmya.carleaseplatform.service;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sowmya.carleaseplatform.constants.CarLeaseServiceConstants;
import com.sowmya.carleaseplatform.dto.CarDetailsResponse;
import com.sowmya.carleaseplatform.dto.CarLeaseCalculationRequest;
import com.sowmya.carleaseplatform.dto.CarLeaseRequest;
import com.sowmya.carleaseplatform.dto.CustomerDetailsResponse;
import com.sowmya.carleaseplatform.dto.LeaseDetailsResponse;
import com.sowmya.carleaseplatform.dto.ResponseDto;
import com.sowmya.carleaseplatform.entity.CarDetailsEntity;
import com.sowmya.carleaseplatform.entity.CarLeaseDetailsEntity;
import com.sowmya.carleaseplatform.exception.CarLeaseException;
import com.sowmya.carleaseplatform.exception.DetailsNotFoundException;
import com.sowmya.carleaseplatform.feign.FeignConsumer;
import com.sowmya.carleaseplatform.repository.CarDetailsRepository;
import com.sowmya.carleaseplatform.repository.CarLeaseRepository;
import jakarta.transaction.Transactional;

/**
 * @author Sowmya Suresh
 *
 */
@Service
public class CarLeaseDetailsServiceImpl implements CarLeaseDetailsService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  CarLeaseRepository carLeaseRepository;

  @Autowired
  CarDetailsRepository carDetailsRepository;

  @Autowired
  FeignConsumer feignConsumer;

  /**
   * Calculates lease rate for a given input
   * 
   * @param Input to calculate lease rate
   * @return lease rate value
   */
  public Double calculateLeaseRate(CarLeaseCalculationRequest request) {
    logger.info("Entering into CarLeaseDetailsServiceImpl.calculateLeaseRate");
    CarDetailsEntity carDetails =
        carDetailsRepository.findByCarNumberIgnoreCase(request.getCarNumber()).orElseThrow(
            () -> new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND));
    logger.info("Exiting CarLeaseDetailsServiceImpl.calculateLeaseRate");
    return (((carDetails.getMileage() / 12) * request.getDuration()) / carDetails.getNetPrice())
        + (((request.getInterestRate() / 100) * carDetails.getNetPrice()) / 12);

  }

  /**
   * Creates a new lease. It validates if the car and customer details are available already and
   * throws an error if not available. It checks if a car is already tagged to a lease and throws
   * exception appropriately.
   * 
   * @param car, customer and lease details
   * @return Lease ID created
   */
  @Transactional
  public Long createNewLease(CarLeaseRequest request) {
    logger.info("Entering into CarLeaseDetailsServiceImpl.createNewLease");
    CarDetailsEntity carDetails =
        carDetailsRepository.findByCarNumberIgnoreCase(request.getCarNumber()).orElseThrow(
            () -> new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND));
    try {
      feignConsumer.findCustomerByEmailAddress(request.getCustomerEmail());
    } catch (Exception ex) {
      throw new DetailsNotFoundException(CarLeaseServiceConstants.CUSTOMER_NOT_FOUND);
    }
    if (carDetails.getCarLeaseDetails() == null) {
      logger.debug("Car and customer details are validated.");
      CarLeaseDetailsEntity newLease = CarLeaseDetailsEntity.builder()
          .duration(request.getDuration()).interestRate(request.getInterestRate())
          .leaseRate(request.getLeaseRate()).customerEmail(request.getCustomerEmail())
          .carDetails(carDetails).createdUser(request.getUserId())
          .createdTimestamp(new Timestamp(System.currentTimeMillis())).build();

      CarLeaseDetailsEntity savedLease = carLeaseRepository.save(newLease);

      return savedLease.getLeaseId();
    } else {
      throw new CarLeaseException(CarLeaseServiceConstants.CAR_UNAVAILABLE_FOR_LEASE);
    }


  }

  /**
   * This method finds details of a lease based on lease ID. It returns details of the car and
   * customer tagged to a lease.
   * 
   * @param lease ID
   * @return Lease details
   */
  @Transactional
  public LeaseDetailsResponse findLeaseDetails(Long leaseId) {
    logger.info("Entering into CarLeaseDetailsServiceImpl.findLeaseDetails");
    CarLeaseDetailsEntity carLeaseDetails = carLeaseRepository.findById(leaseId)
        .orElseThrow(() -> new DetailsNotFoundException(CarLeaseServiceConstants.LEASE_NOT_FOUND));
    CustomerDetailsResponse customerDetails =
        feignConsumer.findCustomerByEmailAddress(carLeaseDetails.getCustomerEmail());

    CarDetailsResponse carDetails =
        CarDetailsResponse.builder().carNumber(carLeaseDetails.getCarDetails().getCarNumber())
            .make(carLeaseDetails.getCarDetails().getMake())
            .model(carLeaseDetails.getCarDetails().getModel())
            .version(carLeaseDetails.getCarDetails().getVersion())
            .noOfDoors(carLeaseDetails.getCarDetails().getNoOfDoors())
            .co2Emission(carLeaseDetails.getCarDetails().getCo2Emission())
            .grossPrice(carLeaseDetails.getCarDetails().getGrossPrice())
            .netPrice(carLeaseDetails.getCarDetails().getNetPrice()).build();
    LeaseDetailsResponse leaseDetails =
        LeaseDetailsResponse.builder().leaseId(carLeaseDetails.getLeaseId())
            .duration(carLeaseDetails.getDuration()).interestRate(carLeaseDetails.getInterestRate())
            .leaseRate(carLeaseDetails.getLeaseRate()).customerDetails(customerDetails)
            .carDetails(carDetails).responseCode(CarLeaseServiceConstants.SUCCESS_CODE).build();

    logger.info("Exiting CarLeaseDetailsServiceImpl.findLeaseDetails");
    return leaseDetails;
  }

  /**
   * This method deletes a lease based on its ID.
   * 
   * @param lease ID
   * @return response code and response message
   */
  @Transactional
  public ResponseDto deleteLeaseDetails(Long leaseId) {
    logger.info("Entering into CarLeaseDetailsServiceImpl.deleteLeaseDetails");
    ResponseDto response = new ResponseDto();
    Long count = carLeaseRepository.deleteByLeaseId(leaseId);
    if (count > 0) {
      response = ResponseDto.builder().responseCode(CarLeaseServiceConstants.SUCCESS_CODE)
          .responseMessage(CarLeaseServiceConstants.LEASE_DELETE_SUCCESS).build();
      logger.debug(CarLeaseServiceConstants.LEASE_DELETE_SUCCESS);
    } else {
      throw new DetailsNotFoundException(CarLeaseServiceConstants.CAR_NOT_FOUND);
    }
    logger.info("Entering into CarLeaseDetailsServiceImpl.deleteLeaseDetails");
    return response;
  }


}
