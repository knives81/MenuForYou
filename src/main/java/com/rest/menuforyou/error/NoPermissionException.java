package com.rest.menuforyou.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException {

	private static final long serialVersionUID = -1L;

	public NoPermissionException() {
	}

	public NoPermissionException(String message) {
		super(message);
	}

	public NoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}

}
