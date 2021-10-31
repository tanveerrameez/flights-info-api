package com.travix.medusa.busyflights.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.experimental.UtilityClass;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

@UtilityClass
public class CurrencyUtils {

	/**
	 * 
	 * @param price
	 * @return BigDecimal value of the final fare
	 */
	public BigDecimal calculateFare(double price) {
		return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * 
	 * @param basePrice
	 *            Price without tax(doesn't include discount)
	 * @param tax
	 *            Tax which needs to be charged along with the price
	 * @param discount
	 *            Discount which needs to be applied on the price(in percentage)
	 * @return BigDecimal value of the final fare
	 */
	public BigDecimal calculateFare(double basePrice, double tax, double discount) {
		BigDecimal priceWithTax = BigDecimal.valueOf(basePrice + tax);
		BigDecimal discountValue = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN));
		return priceWithTax.subtract(priceWithTax.multiply(discountValue)).setScale(2, RoundingMode.HALF_EVEN);

	}

}
