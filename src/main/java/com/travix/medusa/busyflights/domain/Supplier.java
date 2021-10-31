package com.travix.medusa.busyflights.domain;

import com.travix.medusa.busyflights.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier<T, U> {
	private String name;
	private String flightsUrl;
	private Converter<T, U> converter;

}
