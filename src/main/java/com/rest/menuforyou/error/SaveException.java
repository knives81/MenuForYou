package com.rest.menuforyou.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaveException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	public SaveException() {
	}

	public SaveException(String message) {
		super(message);
	}

	public SaveException(String message, Throwable cause) {
		super(message, cause);
	}

}
