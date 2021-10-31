package com.travix.medusa.busyflights.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import lombok.experimental.UtilityClass;

/**
 * @author tanveerrameez, 30 Oct 2021
 */
/**
 * Utility class to convert various date formats. To be refactored to convert between date formats like Instant to LocalDateTime annd vice versa ,
 * rather than converting frpm String to Date Formats
 * 
 * @author tanveerrameez
 *
 */
@UtilityClass
public class DateUtils {

	final DateTimeFormatter isoLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public LocalDate getLocalDate(String localDateInString) {
		return localDateInString == null || localDateInString.trim().length() == 0 ? null : LocalDate.parse(localDateInString, isoLocalDate);
	}

	public String convertISOLocalDateTimeToISODateTime(String isoLocalDateTime) {
		if (isoLocalDateTime == null || isoLocalDateTime.trim().length() == 0)
			return null;
		else {
			LocalDateTime ldt = LocalDateTime.parse(isoLocalDateTime);
			ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());

			return DateTimeFormatter.ISO_DATE_TIME.format(zdt);
		}

	}

	public String convertISODateTimeToISOLocalDateTime(String isoDateTime) {
		if (isoDateTime == null || isoDateTime.trim().length() == 0)
			return null;
		else {
			TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_DATE_TIME.parse(isoDateTime);
			return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(temporalAccessor);
		}

	}

	public String convertIsoInstantToIsoDateTime(String isoInstant) {
		if (isoInstant == null || isoInstant.trim().length() == 0)
			return null;
		else {
			Instant instant = Instant.parse(isoInstant);

			LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
			return DateTimeFormatter.ISO_DATE_TIME.format(datetime);
		}
	}

}
