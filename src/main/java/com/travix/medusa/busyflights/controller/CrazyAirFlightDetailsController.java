package com.travix.medusa.busyflights.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@RestController
@RequestMapping(path = "/crazy-air")
public class CrazyAirFlightDetailsController extends BaseController {

	public CrazyAirFlightDetailsController() {

	}

	@PostMapping("/get-flights")
	@ResponseStatus(HttpStatus.OK)
	public List<CrazyAirResponse> getFlights(@RequestBody @Valid final CrazyAirRequest request) {
		// log.debug("create...");

		return getDummyCrazyAirResponse(request);
	}

	private List<CrazyAirResponse> getDummyCrazyAirResponse(final CrazyAirRequest request) {
		List<CrazyAirResponse> crazyAirResponse = new ArrayList<>();
		crazyAirResponse.add(CrazyAirResponse.builder().airline("EasyJet").cabinclass("E").departureAirportCode(request.getOrigin()).price(200.50)
				.departureDate("2011-12-03T09:15:30").arrivalDate("2011-12-03T12:15:30").destinationAirportCode(request.getDestination()).build());

		crazyAirResponse.add(CrazyAirResponse.builder().airline("EasyJet").cabinclass("B").departureAirportCode(request.getDestination()).price(180)
				.departureDate("2021-12-13T11:05:00").arrivalDate("2021-12-13T15:25:00").destinationAirportCode(request.getOrigin()).build());
		return crazyAirResponse;

	}

}
