package com.travix.medusa.busyflights.service;

import java.util.Set;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.exception.ServiceException;

public interface BusyFlightDetailsService {
	Set<BusyFlightsResponse> getFlights(BusyFlightsRequest request) throws ServiceException;

}
