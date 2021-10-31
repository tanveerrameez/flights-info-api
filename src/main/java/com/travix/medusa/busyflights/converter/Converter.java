package com.travix.medusa.busyflights.converter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public interface Converter<T, U> {

	T convertRequest(BusyFlightsRequest input);
	BusyFlightsResponse convertResponse(U input);
	Optional<List<U>> deserialiseJson(String json) throws IOException;
}
