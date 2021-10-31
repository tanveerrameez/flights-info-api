package com.travix.medusa.busyflights.domain.busyflights;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.travix.medusa.busyflights.validation.BusyFlightsRequestDateValidation;

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
@BusyFlightsRequestDateValidation
public class BusyFlightsRequest {

	@NotEmpty(message = "Flight Origin must be provided")
	@Size(min = 3, max = 3, message = "Flight Origin must be 3 letters")
	private String origin;

	@NotEmpty(message = "Flight destination must be provided")
	@Size(min = 3, max = 3, message = "Flight destination must be 3 letters")
	private String destination;

	/**
	 * Converting String to LocalDate for easy date formatting and validation
	 */
	@NotNull(message = "Departure date must be provided")
	@FutureOrPresent
	private LocalDate departureDate;

	@FutureOrPresent
	private LocalDate returnDate;

	@NotNull(message = "Number of passengers must be provided")
	@Range(min = 1, max = 4, message = "Number of passengers must between 1 and 4")
	private int numberOfPassengers;

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

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(final int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}
}
