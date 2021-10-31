package com.travix.medusa.busyflights.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@RestController
@RequestMapping(path = "/tough-jet")
public class ToughJetFlightDetailsController extends BaseController {

	public ToughJetFlightDetailsController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping("/get-flights")
	@ResponseStatus(HttpStatus.CREATED)
	public Set<ToughJetResponse> getFlights(@RequestBody @Valid final ToughJetRequest request) {
		// log.debug("create...");

		return getDummyToughJetResponse(request);
	}

	/**
	 * Returns dummy data. Ideally this api will be an external one
	 * 
	 * @param request
	 * @return
	 */
	private Set<ToughJetResponse> getDummyToughJetResponse(final ToughJetRequest request) {
		Set<ToughJetResponse> toughJetResponse = new HashSet<>();

		toughJetResponse.add(ToughJetResponse.builder().carrier("Alitalia").departureAirportName("FUE").outboundDateTime("2021-12-03T14:30:00.108Z")
				.inboundDateTime("2021-12-03T10:38:00.108Z").arrivalAirportName("LGW").basePrice(80).tax(13).discount(5).build());
		toughJetResponse.add(ToughJetResponse.builder().carrier("Alitalia").departureAirportName("LGW").outboundDateTime("2021-12-13T18:20:00.108Z")
				.inboundDateTime("2021-12-13T16:10:00.108Z").arrivalAirportName("FUE").basePrice(90).tax(18).discount(5).build());

		return toughJetResponse;

	}

}
