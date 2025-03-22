package com.example.demo.exception;

import java.util.Map;

public class ValidationEcxception extends RuntimeException{
	
	private Map<String, Object> error;
	
	public ValidationEcxception(Map<String, Object> error)
	{
		super("Validation Failed");
		this.error = error;
		
	}
	
	public Map<String, Object> getErrors()
	{
		return error;
	}
	
}
