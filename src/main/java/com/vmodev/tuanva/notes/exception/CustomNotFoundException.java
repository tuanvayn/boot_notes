package com.vmodev.tuanva.notes.exception;

public class CustomNotFoundException extends RuntimeException {
	public CustomNotFoundException() {
		super();
	}

	public CustomNotFoundException(String msg) {
		super(msg);
	}
}
