package com.travix.medusa.busyflights.converter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.travix.medusa.busyflights.domain.SupplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.utils.CurrencyUtils;
import com.travix.medusa.busyflights.utils.DateUtils;
import com.travix.medusa.busyflights.utils.JsonMapper;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public class CrazyAirConverter implements Converter<CrazyAirRequest, CrazyAirResponse> {

	public CrazyAirConverter() {

	}

	@Override
	public CrazyAirRequest convertRequest(BusyFlightsRequest input) {
		// both request model have ISO_LOCAL_DATE format, so no need to change format
		return CrazyAirRequest.builder().origin(input.getOrigin()).destination(input.getDestination()).departureDate(input.getDepartureDate())
				.returnDate(input.getReturnDate()).passengerCount(input.getNumberOfPassengers()).build();
	}

	@Override
	public BusyFlightsResponse convertResponse(CrazyAirResponse input) {

		return BusyFlightsResponse.builder().airline(input.getAirline())
				// need to convert date format
				.arrivalDate(DateUtils.convertISOLocalDateTimeToISODateTime(input.getArrivalDate()))
				.departureDate(DateUtils.convertISOLocalDateTimeToISODateTime(input.getDepartureDate()))
				.departureAirportCode(input.getDepartureAirportCode()).destinationAirportCode(input.getDestinationAirportCode())
				// round up fare
				.fare(CurrencyUtils.calculateFare(input.getPrice())).supplier(SupplierEnum.CRAZYAIR.name()).build();
	}

	@Override
	public Optional<List<CrazyAirResponse>> deserialiseJson(String json) throws IOException {
		if (json != null && json.trim().length() > 0)
			return Optional.of(JsonMapper.readValueToList(json, CrazyAirResponse.class));
		else
			return Optional.empty();
	}

}
