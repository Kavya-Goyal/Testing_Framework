package io.learn.exception;

public class InvalidProductSelectionException extends RuntimeException {
	public InvalidProductSelectionException(String message) {
		super(message);
	}
}
