package com.travix.medusa.busyflights.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

/**
 * Implementation that encapsulates RestTemplate
 * 
 * @author tanveerrameez
 *
 */
@Service
@Log4j
public class RestTemplateServiceImpl implements RestTemplateService {

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public <T> ResponseEntity<T> invokeGetAsResponseEntity(String url, Class<T> responseType) {
		return restTemplate.getForEntity(url, responseType);

	}

	@Override
	public <T> T invokeGet(String url, Class<T> responseType) {
		return restTemplate.getForObject(url, responseType);
	}

	@Override
	public String post(String url, Object dto) {
		HttpEntity<Object> request = new HttpEntity<>(dto);
		return restTemplate.postForObject(url, request, String.class);
	}

}
