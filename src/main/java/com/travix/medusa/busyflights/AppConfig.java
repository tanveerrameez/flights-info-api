package com.travix.medusa.busyflights;

import java.util.Comparator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travix.medusa.busyflights.converter.CrazyAirConverter;
import com.travix.medusa.busyflights.converter.ToughJetConverter;
import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/**
 * @author tanveerrameez, 30 Oct 2021
 */
@Configuration
public class AppConfig {

	@Bean
	public Supplier<CrazyAirRequest, CrazyAirResponse> crazyAirSupplier() {
		Supplier<CrazyAirRequest, CrazyAirResponse> crazyAirSupplier = new Supplier<>();
		crazyAirSupplier.setName("CrazyAir");
		crazyAirSupplier.setFlightsUrl("http://localhost:8080/crazy-air/get-flights");
		crazyAirSupplier.setConverter(new CrazyAirConverter());
		return crazyAirSupplier;
	}

	@Bean
	public Supplier<ToughJetRequest, ToughJetResponse> toughJetSupplier() {
		Supplier<ToughJetRequest, ToughJetResponse> toughJetSupplier = new Supplier<>();
		toughJetSupplier.setName("ToughJet");
		toughJetSupplier.setFlightsUrl("http://localhost:8080/tough-jet/get-flights");
		toughJetSupplier.setConverter(new ToughJetConverter());
		return toughJetSupplier;
	}

	@Bean
	public Comparator<BusyFlightsResponse> busyFlightsComparator() {
		return Comparator.comparing(BusyFlightsResponse::getFare);
	}

}
