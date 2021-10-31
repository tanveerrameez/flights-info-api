package com.travix.medusa.busyflights.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.exception.ServiceException;
import com.travix.medusa.busyflights.exception.ValidationException;
import com.travix.medusa.busyflights.service.BusyFlightDetailsService;
import com.travix.medusa.busyflights.utils.DateUtils;
import com.travix.medusa.busyflights.utils.ValidationUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@Api(value = "/busyflights", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping(path = "/busyflights")

@Log4j
public class BusyFlightDetailsController extends BaseController {

	private final BusyFlightDetailsService busyFlightDetailsService;

	public BusyFlightDetailsController(final BusyFlightDetailsService busyFlightDetailsService) {
		this.busyFlightDetailsService = busyFlightDetailsService;
	}

	@ApiOperation(value = "Returns all available flights", response = List.class, responseContainer = "List", tags = "getFlights")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 422, message = "Unprocessable Entity"),
			@ApiResponse(code = 400, message = "Bad Request")})
	@ResponseBody
	@GetMapping("/flights")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Set<BusyFlightsResponse>> getFlights(
			@ApiParam(name = "origin", type = "String", value = "3 letter IATA code(eg. LHR, AMS)", example = "LHR", required = true) @RequestParam(value = "origin", required = true) final String origin,
			@ApiParam(name = "destination", type = "String", value = "3 letter IATA code(eg. LHR, AMS)", example = "FUE", required = true) @RequestParam(value = "destination", required = true) final String destination,
			@ApiParam(name = "departureDate", type = "String", value = "ISO_LOCAL_DATE format", example = "2021-12-03", required = true) @RequestParam(value = "departureDate", required = true) final String departureDate,
			@ApiParam(name = "returnDate", type = "String", value = "ISO_LOCAL_DATE format", example = "2021-12-13", required = true) @RequestParam(value = "returnDate", required = true) final String returnDate,
			@ApiParam(name = "numberOfPassengers", type = "String", value = "Maximum 4 passengers", example = "3", required = true) @RequestParam(value = "numberOfPassengers", required = true) final int numberOfPassengers)
			throws ServiceException, ValidationException {
		log.debug("invoking getFlights...");
		// validate data, max 4 passenger, origin/destination- 3 letter
		BusyFlightsRequest request = BusyFlightsRequest.builder().departureDate(DateUtils.getLocalDate(departureDate))
				.returnDate(DateUtils.getLocalDate(returnDate)).destination(destination).origin(origin).numberOfPassengers(numberOfPassengers)
				.build();

		ValidationUtils.validate(request);

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/flights")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Set<BusyFlightsResponse>> getFlightsUsingPost(
			@ApiParam(name = "account", type = "Account", value = "Account data to be created", required = true) @RequestBody @Valid final BusyFlightsRequest request)
			throws ServiceException {
		log.debug("invoking getFlightsUsingPost...");
		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
