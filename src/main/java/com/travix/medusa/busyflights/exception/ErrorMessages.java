package com.travix.medusa.busyflights.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author tanveerrameez, 31 Oct 2021
 */
@AllArgsConstructor
@Getter
@ToString
public class ErrorMessages {

	private Object object;
	private List<String> messages;

}
