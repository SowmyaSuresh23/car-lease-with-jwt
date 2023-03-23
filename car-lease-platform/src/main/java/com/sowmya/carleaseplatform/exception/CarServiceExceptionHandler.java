package com.sowmya.carleaseplatform.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.sowmya.carleaseplatform.constants.CarLeaseServiceConstants;
import com.sowmya.carleaseplatform.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

/**
 * @author Sowmya Suresh
 *
 */
@ControllerAdvice
public class CarServiceExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseDto handleInvalidArgument(
      final MethodArgumentNotValidException exception, final HttpServletRequest request) {

    logger.error(exception.getMessage());
    ResponseDto error = new ResponseDto();
    StringBuilder responseMessage = new StringBuilder();
    exception.getBindingResult().getAllErrors().stream().forEach(errors -> {
      responseMessage.append(errors.getDefaultMessage()).append(System.lineSeparator());
    });
    error.setResponseMessage(responseMessage.toString());
    error.setResponseCode(CarLeaseServiceConstants.BAD_REQUEST);

    return error;
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseDto handleInvalidParameter(
      final MissingServletRequestParameterException exception, final HttpServletRequest request) {

    logger.error(exception.getMessage());
    ResponseDto error = new ResponseDto();
    error.setResponseMessage(exception.getMessage());
    error.setResponseCode(CarLeaseServiceConstants.BAD_REQUEST);

    return error;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ResponseDto handleConstraintViolation(
      final ConstraintViolationException exception, final HttpServletRequest request) {

    logger.error(exception.getMessage());
    ResponseDto error = new ResponseDto();
    StringBuilder responseMessage = new StringBuilder();
    exception.getConstraintViolations().stream().forEach(errors -> {
      responseMessage.append(errors.getMessage());
    });
    error.setResponseMessage(responseMessage.toString());
    error.setResponseCode(CarLeaseServiceConstants.BAD_REQUEST);

    return error;
  }

  @ExceptionHandler(DetailsNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody ResponseDto handleCarNotFoundException(
      final DetailsNotFoundException exception, final HttpServletRequest request) {

    logger.error(exception.getErrorMessage());
    ResponseDto error = new ResponseDto();
    error.setResponseMessage(exception.getErrorMessage());
    error.setResponseCode(CarLeaseServiceConstants.NOT_FOUND);

    return error;
  }

  @ExceptionHandler(CarAlreadyExistsException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public @ResponseBody ResponseDto handleCarAlreadyExistsException(
      final CarAlreadyExistsException exception, final HttpServletRequest request) {

    logger.error(exception.getErrorMessage());
    ResponseDto error = new ResponseDto();
    error.setResponseMessage(exception.getErrorMessage());
    error.setResponseCode(CarLeaseServiceConstants.CONFLICT);

    return error;
  }

  @ExceptionHandler(CarLeaseException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public @ResponseBody ResponseDto handleCarLeaseException(final CarLeaseException exception,
      final HttpServletRequest request) {

    logger.error(exception.getErrorMessage());
    ResponseDto error = new ResponseDto();
    error.setResponseMessage(exception.getErrorMessage());
    error.setResponseCode(CarLeaseServiceConstants.CONFLICT);

    return error;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ResponseDto handleGeneralException(final Exception exception,
      final HttpServletRequest request) {

    logger.error(exception.getMessage());
    ResponseDto error = new ResponseDto();
    error.setResponseMessage(CarLeaseServiceConstants.SYSTEM_ERROR);
    error.setResponseCode(CarLeaseServiceConstants.INTERNAL_SERVER_ERROR);

    return error;
  }

}
