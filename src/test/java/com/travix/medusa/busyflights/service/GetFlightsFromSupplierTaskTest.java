package com.travix.medusa.busyflights.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.travix.medusa.busyflights.DataGenerator;
import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.SupplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.utils.JsonMapper;

/**
 * @author tanveerrameez, 31 Oct 2021
 */

@RunWith(MockitoJUnitRunner.class)
public class GetFlightsFromSupplierTaskTest {

	private GetFlightsFromSupplierTask task;
	private Supplier<CrazyAirRequest, CrazyAirResponse> crazyAirSupplier = DataGenerator.getCrazyAirSupplier();

	@Mock
	private RestTemplateService restTemplateService;

	@Before
	public void setUp() {
		task = new GetFlightsFromSupplierTask(crazyAirSupplier, restTemplateService, DataGenerator.createBusyFlightsRequest());
	}

	@Test
	public void testGetFlightsCall() throws Exception {
		List<CrazyAirResponse> crazyAirResponses = DataGenerator.getCrazyAirResponse();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		Set<BusyFlightsResponse> responses = task.call();
		assertNotNull(responses);
		assertEquals(2, responses.size());
		responses.forEach(response -> {
			assertNotNull(response.getAirline());
			assertNotNull(response.getArrivalDate());
			assertNotNull(response.getDepartureDate());
			assertNotNull(response.getDepartureAirportCode());
			assertNotNull(response.getDestinationAirportCode());
			assertNotNull(response.getFare());
			assertNotNull(response.getSupplier());
			assertEquals(SupplierEnum.CRAZYAIR.name(), response.getSupplier());
		});

		/**
		 * The following can be optmised/refactored to simplify
		 */
		List<BusyFlightsResponse> busyFlightResponses = new ArrayList<>(responses);
		List<BusyFlightsResponse> busyFlightResponsesFromCrazyAirResponses = new ArrayList<>();
		crazyAirResponses.forEach(crazyAirResponse -> {
			busyFlightResponsesFromCrazyAirResponses.add(crazyAirSupplier.getConverter().convertResponse(crazyAirResponse));
		});

		assertEquals(busyFlightResponsesFromCrazyAirResponses.size(), busyFlightResponses.size());
		busyFlightResponsesFromCrazyAirResponses.forEach(response -> assertTrue(busyFlightResponses.contains(response)));

	}

	@Test
	public void testGetFlightsCallWithNoData() throws Exception {
		List<CrazyAirResponse> crazyAirResponses = DataGenerator.getCrazyAirResponse();
		doReturn(null).when(restTemplateService).post(eq("http://crazyair.com/flight/data"), any(CrazyAirRequest.class));
		Set<BusyFlightsResponse> responses = task.call();
		assertNotNull(responses);
		assertEquals(0, responses.size());

		doReturn("").when(restTemplateService).post(eq("http://crazyair.com/flight/data"), any(CrazyAirRequest.class));
		responses = task.call();
		assertNotNull(responses);
		assertEquals(0, responses.size());
	}

	@Test(expected = JsonParseException.class)
	public void testGetFlightsCallInvalidJson() throws Exception {
		List<CrazyAirResponse> crazyAirResponses = DataGenerator.getCrazyAirResponse();
		doReturn(null).when(restTemplateService).post(eq("http://crazyair.com/flight/data"), any(CrazyAirRequest.class));
		Set<BusyFlightsResponse> responses = task.call();
		assertNotNull(responses);
		assertEquals(0, responses.size());

		doReturn("invalid").when(restTemplateService).post(eq("http://crazyair.com/flight/data"), any(CrazyAirRequest.class));
		responses = task.call();
	}

	@Test
	public void testGetFlightsCallWithOneInvalidResponse() throws Exception {
		List<CrazyAirResponse> crazyAirResponses = DataGenerator.getCrazyAirResponse();
		crazyAirResponses.get(1).setDepartureAirportCode(null);
		doReturn(JsonMapper.writeValueAsString(crazyAirResponses)).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		Set<BusyFlightsResponse> responses = task.call();
		assertNotNull(responses);
		assertEquals(1, responses.size());
	}

}
