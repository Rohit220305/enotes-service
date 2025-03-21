package com.example.demo.exception;

import java.net.spi.InetAddressResolver.LookupPolicy;
import java.security.PublicKey;

import org.springframework.boot.ExitCodeEvent;

public class ResourceNotFoundException extends Exception {
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
