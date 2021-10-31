package com.travix.medusa.busyflights.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author tanveerrameez, 31 Oct 2021
 */

// @Repeatable(DateValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateValidator.class})
@Documented
public @interface BusyFlightsRequestDateValidation {

	String message() default "Field is required.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}