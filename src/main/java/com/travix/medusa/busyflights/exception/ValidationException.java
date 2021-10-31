package com.travix.medusa.busyflights.exception;

import java.util.List;

import lombok.Getter;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@Getter
public class ValidationException extends Exception {

	private static final long serialVersionUID = 4341658576478704202L;
	private final List<String> messages;

	public ValidationException(List<String> messages) {
		this.messages = messages;
	}

}
