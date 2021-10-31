package com.travix.medusa.busyflights.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.travix.medusa.busyflights.DataGenerator;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.utils.JsonMapper;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BusyFlightsDetailsControllerTest {

	@Autowired
	private BusyFlightDetailsController controller;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetFlights() throws Exception {

		String json = mockMvc.perform(get("/busyflights/flights" + generateQueryParams("FUE", "LGW", "2021-12-03", "2021-12-13", 2)))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn().getResponse()
				.getContentAsString();
		List<BusyFlightsResponse> busyFlights = JsonMapper.readValueToList(json, BusyFlightsResponse.class);
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

	@Test
	public void testGetFlightsWithIncorrectOriginCode() throws Exception {

		String json = mockMvc.perform(get("/busyflights/flights" + generateQueryParams("FUEX", "LGW", "2021-12-03", "2021-12-13", 2)))
				.andExpect(status().isUnprocessableEntity()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn()
				.getResponse().getContentAsString();
		List<String> busyFlights = JsonMapper.readValueToList(json, String.class);
		assertNotNull(busyFlights);
		assertTrue(busyFlights.size() > 0);
		assertTrue(busyFlights.contains("Flight Origin must be 3 letters"));

	}

	@Test
	public void testGetFlightsWithInvalidtNumberPassengere() throws Exception {

		String json = mockMvc.perform(get("/busyflights/flights" + generateQueryParams("FUE", "LGW", "2021-12-03", "2021-12-13", 20)))
				.andExpect(status().isUnprocessableEntity()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn()
				.getResponse().getContentAsString();
		List<String> busyFlights = JsonMapper.readValueToList(json, String.class);
		assertNotNull(busyFlights);
		assertTrue(busyFlights.size() > 0);
		assertTrue(busyFlights.contains("Number of passengers must between 1 and 4"));

	}

	@Test
	public void testGetFlightsWithWrongDateFormat() throws Exception {

		MockHttpServletResponse response = mockMvc
				.perform(get("/busyflights/flights" + generateQueryParams("FUEX", "LGW", "12-03-2021", "2021-12-13", 2)))
				.andExpect(status().isUnprocessableEntity()).andReturn().getResponse();
		String type = response.getContentType();
		String message = response.getContentAsString();
		assertNotNull(message);
		assertTrue(message.contains("'12-03-2021' could not be parsed"));

	}

	@Test
	public void testGetFlightsWithREturnDateBeforeDepartureDate() throws Exception {

		String json = mockMvc.perform(get("/busyflights/flights" + generateQueryParams("FUE", "LGW", "2021-12-03", "2021-11-13", 2)))
				.andExpect(status().isUnprocessableEntity()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn()
				.getResponse().getContentAsString();
		List<String> busyFlights = JsonMapper.readValueToList(json, String.class);
		assertNotNull(busyFlights);
		assertTrue(busyFlights.size() > 0);
		containsMessage(busyFlights, new String[]{"Departure date cannot be after return date"});

	}

	private void containsMessage(List<String> messages, String[] expectedMessages) {

		boolean found = false;
		for (String expectedMessage : expectedMessages) {
			found = false;
			for (String message : messages) {
				if (message.contains(expectedMessage)) {
					found = true;
					break;
				}
			}
			if (!found)
				fail(String.format("message %s not found", expectedMessage));
		}
	}

	@Test
	public void testGetFlightsWithPost() throws Exception {

		String inputJson = JsonMapper.writeValueAsString(DataGenerator.createBusyFlightsRequest());
		String json = mockMvc.perform(post("/busyflights/flights/").contentType(MediaType.APPLICATION_JSON_UTF8).content(inputJson))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		List<BusyFlightsResponse> busyFlights = JsonMapper.readValueToList(json, BusyFlightsResponse.class);
		assertNotNull(busyFlights);
		assertEquals(4, busyFlights.size());
		assertSortedByFare(busyFlights);

	}

	private String generateQueryParams(String origin, String destination, String departureDate, String returnDate, int numberOfPassengers) {
		return "?origin=" + origin + "&destination=" + destination + "&departureDate=" + departureDate + "&returnDate=" + returnDate
				+ "&numberOfPassengers=" + numberOfPassengers;
	}

	private void assertSortedByFare(List<BusyFlightsResponse> response) {

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

}
