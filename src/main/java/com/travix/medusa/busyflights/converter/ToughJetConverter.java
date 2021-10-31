package com.travix.medusa.busyflights.converter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.travix.medusa.busyflights.domain.SupplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.utils.CurrencyUtils;
import com.travix.medusa.busyflights.utils.JsonMapper;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public class ToughJetConverter implements Converter<ToughJetRequest, ToughJetResponse> {

	public ToughJetConverter() {

	}

	@Override
	public ToughJetRequest convertRequest(BusyFlightsRequest input) {
		// both request model have ISO_LOCAL_DATE format, so no need to change format
		return ToughJetRequest.builder().from(input.getOrigin()).to(input.getDestination()).outboundDate(input.getDepartureDate())
				.inboundDate(input.getReturnDate()).numberOfAdults(input.getNumberOfPassengers()).build();
	}

	@Override
	public BusyFlightsResponse convertResponse(ToughJetResponse input) {

		return BusyFlightsResponse.builder().airline(input.getCarrier())
				// need to convert date format
				.arrivalDate(input.getInboundDateTime()).departureDate(input.getOutboundDateTime())
				.departureAirportCode(input.getDepartureAirportName()).destinationAirportCode(input.getArrivalAirportName())
				// convert fare
				.fare(CurrencyUtils.calculateFare(input.getBasePrice(), input.getTax(), input.getDiscount())).supplier(SupplierEnum.TOUGHJET.name())
				.build();
	}

	@Override
	public Optional<List<ToughJetResponse>> deserialiseJson(String json) throws IOException {

		if (json != null && json.trim().length() > 0)
			return Optional.of(JsonMapper.readValueToList(json, ToughJetResponse.class));
		else
			return Optional.empty();
	}

}
