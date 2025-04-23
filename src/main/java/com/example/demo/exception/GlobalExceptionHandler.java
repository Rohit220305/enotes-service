package com.example.demo.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.example.demo.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e)
	{
		log.error("GlobalExceptionHandler :: handleException ::",e.getMessage());

//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErroeResponseMessage(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(Exception e)
	{
		log.error("GlobalExceptionHandler :: handleIllegalArgumentException ::",e.getMessage());

		return CommonUtil.createErroeResponseMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e)
	{
		log.error("GlobalExceptionHandler :: handleNullPointerException ::",e.getMessage());

		return CommonUtil.createErroeResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotException(Exception e)
	{
		log.error("GlobalExceptionHandler :: handleResourceNotException ::",e.getMessage());
		return CommonUtil.createErroeResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationEcxception.class)
	public ResponseEntity<?> handleValidationEcxception(ValidationEcxception e)
	{
		log.error("GlobalExceptionHandler :: handleValidationEcxception ::",e.getMessage());
		return CommonUtil.createErroeResponse(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e)
	{
		log.error("GlobalExceptionHandler :: handleFileNotFoundException ::",e.getMessage());
		return CommonUtil.createErroeResponse(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
