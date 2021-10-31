package com.travix.medusa.busyflights.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.travix.medusa.busyflights.exception.ValidationException;

import lombok.extern.log4j.Log4j;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@Log4j
public class BaseController {

	public BaseController() {

	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<String> allExceptionHandler(final Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ValidationException.class, MaxUploadSizeExceededException.class})
	protected ResponseEntity<List<String>> validationExceptionHandler(final ValidationException e) {
		e.printStackTrace();
		try {
			log.error("Error sent in response : ");
			for (String message : e.getMessages()) {
				log.error(message);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new ResponseEntity<>(e.getMessages(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
