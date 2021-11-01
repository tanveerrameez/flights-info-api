package com.travix.medusa.busyflights.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.exception.ErrorMessages;
import com.travix.medusa.busyflights.exception.ValidationException;
import com.travix.medusa.busyflights.utils.ValidationUtils;

import lombok.extern.log4j.Log4j;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

/**
 * Callable task that gets the flights information from the supplier
 * 
 * @author tanveerrameez
 *
 * @param <T>
 * @param <U>
 */
@Log4j
public class GetFlightsFromSupplierTask<T, U> implements Callable<Set<BusyFlightsResponse>> {

	private final Supplier<T, U> supplier;
	private final RestTemplateService restTemplateService;
	private final BusyFlightsRequest request;

	public GetFlightsFromSupplierTask(final Supplier<T, U> supplier, final RestTemplateService restTemplateService, BusyFlightsRequest request) {
		this.supplier = supplier;
		this.restTemplateService = restTemplateService;
		this.request = request;
	}

	@Override
	public Set<BusyFlightsResponse> call() throws Exception {

		T convertedRequest = supplier.getConverter().convertRequest(request);
		String responseString = restTemplateService.post(supplier.getFlightsUrl(), convertedRequest);
		return convertToBusyFlightsResponse(supplier, responseString);
	}

	/**
	 * Converts the json String returned from restTemplate call into Set of BusyFlightsResponse
	 * 
	 * @param supplier
	 * @param responseString
	 * @return
	 * @throws IOException
	 */
	private Set<BusyFlightsResponse> convertToBusyFlightsResponse(Supplier<T, U> supplier, String responseString) throws IOException {
		List<U> responseList = supplier.getConverter().deserialiseJson(responseString).orElse(Collections.emptyList());

		final Set<BusyFlightsResponse> busyFlightsResponseList = new HashSet<>();

		responseList.forEach(response -> {
			BusyFlightsResponse busyFlightsResponse = null;
			try {
				busyFlightsResponse = supplier.getConverter().convertResponse(response);

				// only add valid response (i..e no missing mandatory fields or invalid values) to the list
				ValidationUtils.validate(busyFlightsResponse);
				busyFlightsResponseList.add(busyFlightsResponse);

			} catch (ValidationException ve) {
				// log which responses failed with the errors, and continue for next conversion
				log.error(new ErrorMessages(busyFlightsResponse, ve.getMessages()));
			} catch (Exception e) {
				// exception while converting response, log it and continue for next conversion
				log.error(new ErrorMessages(busyFlightsResponse, Arrays.asList(e.getMessage())));
			}

		});
		return busyFlightsResponseList;
	}

}
