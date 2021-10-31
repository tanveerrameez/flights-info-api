package com.travix.medusa.busyflights.service;

import org.springframework.http.ResponseEntity;

public interface RestTemplateService {

	<T> ResponseEntity<T> invokeGetAsResponseEntity(String url, Class<T> responseType);
	<T> T invokeGet(String url, Class<T> responseType);
	String post(String url, Object dto);
}
