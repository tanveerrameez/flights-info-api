package com.travix.medusa.busyflights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travix.medusa.busyflights.converter.CrazyAirConverter;
import com.travix.medusa.busyflights.converter.ToughJetConverter;
import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.utils.DateUtils;
import com.travix.medusa.busyflights.utils.JsonMapper;

import lombok.experimental.UtilityClass;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@UtilityClass
public class DataGenerator {

	private static Supplier<CrazyAirRequest, CrazyAirResponse> crazyAirSupplier;
	private static Supplier<ToughJetRequest, ToughJetResponse> toughJetSupplier;

	static {
		crazyAirSupplier = new Supplier<>();
		crazyAirSupplier.setName("CrazyAir");
		crazyAirSupplier.setFlightsUrl("http://crazyair.com/flight/data");
		crazyAirSupplier.setConverter(new CrazyAirConverter());

		toughJetSupplier = new Supplier<>();
		toughJetSupplier.setName("ToughJet");
		toughJetSupplier.setFlightsUrl("http://toughjet.com/flight/data");
		toughJetSupplier.setConverter(new ToughJetConverter());

	}

	public BusyFlightsRequest createBusyFlightsRequest() {
		return BusyFlightsRequest.builder().origin("FUE").destination("LGW").departureDate(DateUtils.getLocalDate("2021-12-03"))
				.returnDate(DateUtils.getLocalDate("2021-12-13")).numberOfPassengers(2).build();
	}

	public List<CrazyAirResponse> getCrazyAirResponse() {
		List<CrazyAirResponse> crazyAirResponse = new ArrayList<>();
		crazyAirResponse.add(CrazyAirResponse.builder().airline("EasyJet").cabinclass("E").departureAirportCode("FUE").price(200.50)
				.departureDate("2011-12-03T09:15:30").arrivalDate("2011-12-03T12:15:30").destinationAirportCode("LGW").build());

		crazyAirResponse.add(CrazyAirResponse.builder().airline("EasyJet").cabinclass("B").departureAirportCode("LGW").price(180)
				.departureDate("2021-12-13T11:05:00").arrivalDate("2021-12-13T15:25:00").destinationAirportCode("FUE").build());
		return crazyAirResponse;

	}

	public String getCrazyAirResponseAsJson() throws JsonProcessingException {
		return JsonMapper.writeValueAsString(getCrazyAirResponse());
	}

	public List<ToughJetResponse> getToughJetResponse() throws JsonProcessingException {
		List<ToughJetResponse> toughJetResponse = new ArrayList<>();
		toughJetResponse.add(ToughJetResponse.builder().carrier("HeavyJet").departureAirportName("FUE").outboundDateTime("2021-12-03T14:30:00.108Z")
				.inboundDateTime("2021-12-03T10:38:00.108Z").arrivalAirportName("LGW").basePrice(80).tax(12).discount(5).build());
		toughJetResponse.add(ToughJetResponse.builder().carrier("HeavyJet").departureAirportName("LGW").outboundDateTime("2021-12-13T18:20:00.108Z")
				.inboundDateTime("2021-12-13T16:10:00.108Z").arrivalAirportName("FUE").basePrice(95).tax(18).discount(5).build());
		return toughJetResponse;

	}

	public String getToughJetResponseAsJson() throws JsonProcessingException {
		return JsonMapper.writeValueAsString(getToughJetResponse());
	}
	public List<Supplier> listAllSuppliers() {
		return Arrays.asList(crazyAirSupplier, toughJetSupplier);
	}

	public Supplier getCrazyAirSupplier() {
		return crazyAirSupplier;
	}

	public Supplier getToughJetSupplier() {
		return toughJetSupplier;
	}

	public Comparator<BusyFlightsResponse> getBusyFlightsComparator() {
		return Comparator.comparing(BusyFlightsResponse::getFare);
	}

}
