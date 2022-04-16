package com.vamsi.javaRestAPI.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException {
	private static final long serialVersionUID = 1L ;

    public ResourceNotFoundException(String message) {
        super();
    }

    public ResourceNotFoundException(String message, Throwable throwable) {
        super();
    }
}
