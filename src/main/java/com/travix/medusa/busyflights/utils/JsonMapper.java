package com.travix.medusa.busyflights.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.experimental.UtilityClass;

/**
 * Json mapping utility class
 * 
 * @author tanveerrameez, 30 Oct 2021
 */

@UtilityClass
public class JsonMapper {

	public static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.getFactory().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		mapper.findAndRegisterModules();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static <T> T readValue(final String valueAsJson, final Class<T> clazz) throws JsonMappingException, JsonProcessingException, IOException {
		return mapper.readValue(valueAsJson, clazz);

	}

	public static <T> List<T> readValueToList(final String valueAsJson, final Class<T> clazz) throws IOException {
		final JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return mapper.readValue(valueAsJson, type);

	}

	public static String writeValueAsString(final Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}

}