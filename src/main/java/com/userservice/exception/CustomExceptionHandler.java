package com.userservice.exception;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(WebRequest wq, Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorMessage(ex.getMessage());
		errorDetails.setErrorDetails(ex.getLocalizedMessage());
		errorDetails.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleOtherException(WebRequest wq, Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorMessage(ex.getMessage());
		errorDetails.setErrorDetails(ex.getLocalizedMessage());
		errorDetails.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		StringJoiner st = new StringJoiner(",");
		ex.getAllErrors().forEach(x-> st.add(x.getDefaultMessage()));
		errorDetails.setErrorMessage(st.toString());
		errorDetails.setErrorDetails(st.toString());
		errorDetails.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceFoundException(WebRequest wq, Exception ex) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorMessage(ex.getMessage());
		errorDetails.setErrorDetails(ex.getLocalizedMessage());
		errorDetails.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	

}
