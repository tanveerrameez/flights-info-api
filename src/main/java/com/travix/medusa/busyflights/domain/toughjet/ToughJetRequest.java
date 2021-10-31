package com.travix.medusa.busyflights.domain.toughjet;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ToughJetRequest {

	private String from;
	private String to;
	/**
	 * Converting String to LocalDate for easy date formatting and validation
	 */
	private LocalDate outboundDate;
	private LocalDate inboundDate;
	private int numberOfAdults;

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(final String to) {
		this.to = to;
	}

	public LocalDate getOutboundDate() {
		return outboundDate;
	}

	public void setOutboundDate(final LocalDate outboundDate) {
		this.outboundDate = outboundDate;
	}

	public LocalDate getInboundDate() {
		return inboundDate;
	}

	public void setInboundDate(final LocalDate inboundDate) {
		this.inboundDate = inboundDate;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(final int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}
}
