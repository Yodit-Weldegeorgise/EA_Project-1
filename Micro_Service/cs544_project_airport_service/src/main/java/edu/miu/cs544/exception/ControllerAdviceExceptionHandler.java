package edu.miu.cs544.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		logger.info("Start of ResponseStatusException");
		String message = ex.getReason();
//		Integer statusCode =  ex.getStatus().value();
//		String statusName =  ex.getStatus().name();
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), statusCode, statusName, message);
		logger.info("End of ResponseStatusException");
		return new ResponseEntity<>(message, ex.getStatus());
	}
}