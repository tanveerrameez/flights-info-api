package com.travix.medusa.busyflights.domain;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public enum SupplierEnum {
	CRAZYAIR("CrazyAir"), TOUGHJET("ToughJet");

	private String name;
	SupplierEnum(String name) {
		this.name = name;
	}
}
