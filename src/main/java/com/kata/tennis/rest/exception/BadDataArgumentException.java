package com.kata.tennis.rest.exception;

import lombok.Getter;

@Getter
public class BadDataArgumentException extends RuntimeException {

	public BadDataArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadDataArgumentException(String message) {
		super(message);
	}
	
}
