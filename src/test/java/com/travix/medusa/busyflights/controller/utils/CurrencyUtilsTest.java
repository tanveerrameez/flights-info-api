package com.travix.medusa.busyflights.controller.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.travix.medusa.busyflights.utils.CurrencyUtils;

/**
 * @author tanveerrameez, 31 Oct 2021
 */

public class CurrencyUtilsTest {

	@Test
	public void testFareWithTaxAndDiscount() {

		BigDecimal fare = CurrencyUtils.calculateFare(100, 20, 5);
		assertTrue(fare.compareTo(BigDecimal.valueOf(114.00)) == 0);
	}

	@Test
	public void testZeroDiscount() {
		BigDecimal fare = CurrencyUtils.calculateFare(100, 20, 0);
		assertTrue(fare.compareTo(BigDecimal.valueOf(120.00)) == 0);
	}

	@Test
	public void testZeroTax() {
		BigDecimal fare = CurrencyUtils.calculateFare(100, 0, 5);
		assertTrue(fare.compareTo(BigDecimal.valueOf(95.00)) == 0);
	}

	@Test
	public void testZeroPriceAndTax() {
		BigDecimal fare = CurrencyUtils.calculateFare(0, 0, 0);
		assertTrue(fare.compareTo(BigDecimal.valueOf(0)) == 0);
	}

	@Test
	public void testZeroPriceAndNonZeroTax() {
		BigDecimal fare = CurrencyUtils.calculateFare(0, 10, 0);
		assertTrue(fare.compareTo(BigDecimal.valueOf(10)) == 0);
	}

	@Test
	public void testZeroPriceAndNonZeroDiscount() {
		BigDecimal fare = CurrencyUtils.calculateFare(0, 10, 5);
		assertTrue(fare.compareTo(BigDecimal.valueOf(9.50)) == 0);
	}

	@Test
	public void testScale() {
		BigDecimal fare = CurrencyUtils.calculateFare(12.3);
		assertTrue(fare.compareTo(BigDecimal.valueOf(12.30)) == 0);

		fare = CurrencyUtils.calculateFare(12.3456);
		assertTrue(fare.compareTo(BigDecimal.valueOf(12.35)) == 0);

		fare = CurrencyUtils.calculateFare(12.3422);
		assertTrue(fare.compareTo(BigDecimal.valueOf(12.34)) == 0);

		fare = CurrencyUtils.calculateFare(12);
		assertTrue(fare.compareTo(BigDecimal.valueOf(12.00)) == 0);
	}

}
