package com.travix.medusa.busyflights.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.travix.medusa.busyflights.exception.ValidationException;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;

/**
 * @author tanveerrameez, 31 Oct 2021
 */

@UtilityClass
@Log4j
public class ValidationUtils {

	public <T> void validate(T object) throws ValidationException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (violations != null && !violations.isEmpty()) {
			final List<String> messages = new ArrayList<>();
			for (ConstraintViolation<T> violation : violations) {
				log.error(violation.getMessage());
				messages.add(violation.getMessage());
			}

			throw new ValidationException(messages);
		}
	}

}
