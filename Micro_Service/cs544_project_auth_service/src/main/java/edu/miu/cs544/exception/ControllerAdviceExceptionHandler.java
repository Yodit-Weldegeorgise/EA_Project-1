package edu.miu.cs544.exception;

import edu.miu.cs544.service.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationError(AuthenticationException ex) {
		logger.info("Start of handleAuthenticationError");
		String message = ex.getMessage();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), message);
		logger.info("End of handleAuthenticationError");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<ExceptionResponse> handleJwtTokenError(JwtTokenException exception) {

		logger.info("Start of JwtTokenExceptionError");
		String message = exception.getErrorMessage();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.name(), message);
		logger.info("End of JwtTokenExceptionError");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistException(EmailAlreadyExistException ex) {
		logger.info("Start of EmailAlreadyExistException");
		String message = ex.getErrorMessage();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), message);
		logger.info("End of EmailAlreadyExistException");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
		logger.info("Start of ResponseStatusException");
		String message = ex.getResponseBodyAsString();
		System.out.println(ex.getRootCause());
		Integer statusCode = ex.getStatusCode().value();
		String statusName = ex.getStatusCode().name();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), statusCode, statusName, message);
		logger.info("End of ResponseStatusException");
		return new ResponseEntity<>(exceptionResponse, ex.getStatusCode());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> handleAccessDenied(AccessDeniedException ex) {

		logger.info("Start of handleAccessDenied");
		String message = ("You are not authorized to access this resource");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), message);
		logger.info("End of handleAccessDenied");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		logger.info("Start of handleMethodArgumentNotValid");
		String message = ("Syntax Error");
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), message);
		logger.info("End of handleHttpMessageNotReadable");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Start of handleMethodArgumentNotValid");
		String message = ex.getMessage();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), message);
		logger.info("End of handleMethodArgumentNotValid");

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}