package com.travix.medusa.busyflights.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

import lombok.extern.log4j.Log4j;

/**
 * @author tanveerrameez, 31 Oct 2021
 */

@Log4j
public class DateValidator implements ConstraintValidator<BusyFlightsRequestDateValidation, BusyFlightsRequest> {

	@Override
	public void initialize(BusyFlightsRequestDateValidation constraint) {

	}

	@Override
	public boolean isValid(BusyFlightsRequest request, ConstraintValidatorContext constraintContext) {
		boolean valid = true;
		if (request.getDepartureDate().isAfter(request.getReturnDate())) {
			constraintContext.buildConstraintViolationWithTemplate("Departure date cannot be after return date").addPropertyNode("departureDate")
					.addConstraintViolation();
			valid = false;
		}
		return valid;
	}

}