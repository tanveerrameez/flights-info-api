package com.travix.medusa.busyflights.domain.busyflights;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BusyFlightsResponse {

	@NotEmpty(message = "Airline must be provided")
	private String airline;

	@NotEmpty(message = "Supplier must be provided")
	private String supplier;
	/**
	 * fare type has been changed from double to BigDecimal since double has precision issues. Use of BigDecimal is recommended for currency
	 */
	@NotNull(message = "Fare must be provided")
	private BigDecimal fare;

	@NotEmpty(message = "Departure Airport code must be provided")
	@Size(min = 3, max = 3, message = "Flight Origin must be 3 letters")
	private String departureAirportCode;

	@NotEmpty(message = "Destination Airport code must be provided")
	@Size(min = 3, max = 3, message = "Destinationairport code must be 3 letters")
	private String destinationAirportCode;

	@NotNull(message = "Departure date must be provided")
	private String departureDate;

	@NotNull(message = "Arrival date must be provided")
	private String arrivalDate;

}
