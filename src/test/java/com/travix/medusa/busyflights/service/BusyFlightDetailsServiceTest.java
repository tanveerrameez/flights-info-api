package com.travix.medusa.busyflights.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import com.travix.medusa.busyflights.DataGenerator;
import com.travix.medusa.busyflights.TimeoutStubber;
import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class BusyFlightDetailsServiceTest {

	private BusyFlightDetailsServiceImpl busyFlightDetailsService;

	@Mock
	private SupplierLoaderService supplierLoaderService;

	@Mock
	private RestTemplateService restTemplateService;

	@Before
	public void setUp() {
		busyFlightDetailsService = new BusyFlightDetailsServiceImpl(supplierLoaderService, restTemplateService,
				DataGenerator.getBusyFlightsComparator());
	}

	@Test
	public void testGetFlightsForAllSuppliers() throws Exception {

		doReturn(DataGenerator.listAllSuppliers()).when(supplierLoaderService).getActiveFlightSuppliers();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		doReturn(DataGenerator.getToughJetResponseAsJson()).when(restTemplateService).post(eq("http://toughjet.com/flight/data"),
				any(ToughJetRequest.class));

		Set<BusyFlightsResponse> busyFlights = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());
		assertNotNull(busyFlights);
		assertEquals(4, busyFlights.size());
		assertSortedByFare(busyFlights);
		busyFlights.forEach(response -> {
			assertNotNull(response.getAirline());
			assertNotNull(response.getArrivalDate());
			assertNotNull(response.getDepartureDate());
			assertNotNull(response.getDepartureAirportCode());
			assertNotNull(response.getDestinationAirportCode());
			assertNotNull(response.getFare());
			assertNotNull(response.getSupplier());

		});
		/**
		 * Test for valid date format not done. But since the dates should be refactored to appropriate java date types rather than String when
		 * refactored, no need to check validity
		 */

	}

	private void assertSortedByFare(Set<BusyFlightsResponse> response) {

		// test if the set is sorted by fare
		Iterator<BusyFlightsResponse> iter = response.iterator();
		BusyFlightsResponse current, previous = iter.next();
		Comparator<BusyFlightsResponse> busyFlightComparator = Comparator.comparing(BusyFlightsResponse::getFare);
		while (iter.hasNext()) {
			current = iter.next();
			assertFalse(busyFlightComparator.compare(previous, current) > 0);
			previous = current;
		}
	}

	@Test
	public void testGetFlightsForCrazyAir() throws Exception {

		Supplier crazyAirSupplier = DataGenerator.getCrazyAirSupplier();
		doReturn(Arrays.asList(crazyAirSupplier)).when(supplierLoaderService).getActiveFlightSuppliers();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq(crazyAirSupplier.getFlightsUrl()),
				any(CrazyAirRequest.class));

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());

		assertNotNull(response);
		assertEquals(2, response.size());

		// test if the set is sorted by fare
		Iterator<BusyFlightsResponse> iter = response.iterator();
		BusyFlightsResponse current, previous = iter.next();
		Comparator<BusyFlightsResponse> busyFlightComparator = Comparator.comparing(BusyFlightsResponse::getFare);
		while (iter.hasNext()) {
			current = iter.next();
			assertFalse(busyFlightComparator.compare(previous, current) > 0);
			previous = current;
		}

	}

	@Test
	public void testEmptyFlightListForNoSupplier() throws Exception {

		Supplier crazyAirSupplier = DataGenerator.getCrazyAirSupplier();
		doReturn(Collections.emptyList()).when(supplierLoaderService).getActiveFlightSuppliers();

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());

		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	@Test
	public void testExceptionFromOneSupplier() throws Exception {

		doReturn(DataGenerator.listAllSuppliers()).when(supplierLoaderService).getActiveFlightSuppliers();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		doThrow(new NullPointerException()).when(restTemplateService).post(eq("http://toughjet.com/flight/data"), any(ToughJetRequest.class));

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());
		assertNotNull(response);
		assertEquals(2, response.size());
		assertSortedByFare(response);
	}

	@Test
	public void testInvalidDataFromOneSupplier() throws Exception {

		doReturn(DataGenerator.listAllSuppliers()).when(supplierLoaderService).getActiveFlightSuppliers();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		doReturn("invalid").when(restTemplateService).post(eq("http://toughjet.com/flight/data"), any(ToughJetRequest.class));

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());
		assertNotNull(response);
		assertEquals(2, response.size());
		assertSortedByFare(response);
	}

	@Test
	public void testTimeoutExceptionFromOneSupplier() throws Exception {

		doReturn(DataGenerator.listAllSuppliers()).when(supplierLoaderService).getActiveFlightSuppliers();
		doReturn(DataGenerator.getCrazyAirResponseAsJson()).when(restTemplateService).post(eq("http://crazyair.com/flight/data"),
				any(CrazyAirRequest.class));
		TimeoutStubber.doSleep(Duration.ofSeconds(3)).when(restTemplateService).post(eq("http://toughjet.com/flight/data"),
				any(ToughJetRequest.class));

		Set<BusyFlightsResponse> response = busyFlightDetailsService.getFlights(DataGenerator.createBusyFlightsRequest());
		assertNotNull(response);
		assertEquals(2, response.size());
		assertSortedByFare(response);
	}

}
