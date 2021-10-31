package com.travix.medusa.busyflights.domain.crazyair;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CrazyAirRequest {

	private String origin;
	private String destination;
	/**
	 * Converting String to LocalDate for easy date formatting and validation
	 */
	private LocalDate departureDate;
	private LocalDate returnDate;
	private int passengerCount;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(final String destination) {
		this.destination = destination;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(final LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(final LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public int getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(final int passengerCount) {
		this.passengerCount = passengerCount;
	}
}
