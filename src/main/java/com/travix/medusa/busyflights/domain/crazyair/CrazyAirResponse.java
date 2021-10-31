package com.travix.medusa.busyflights.domain.crazyair;

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
public class CrazyAirResponse {

	private String airline;
	private double price;
	private String cabinclass;
	private String departureAirportCode;
	private String destinationAirportCode;

	/**
	 * @todo: field type to be changed to java.time.LocalDateTime
	 */
	private String departureDate;

	/**
	 * @todo: field type to be changed to java.time.LocalDateTime
	 */
	private String arrivalDate;

	public String getAirline() {
		return airline;
	}

	public void setAirline(final String airline) {
		this.airline = airline;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public String getCabinclass() {
		return cabinclass;
	}

	public void setCabinclass(final String cabinclass) {
		this.cabinclass = cabinclass;
	}

	public String getDepartureAirportCode() {
		return departureAirportCode;
	}

	public void setDepartureAirportCode(final String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	}

	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}

	public void setDestinationAirportCode(final String destinationAirportCode) {
		this.destinationAirportCode = destinationAirportCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(final String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(final String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
