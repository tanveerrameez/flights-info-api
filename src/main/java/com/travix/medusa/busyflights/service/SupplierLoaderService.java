package com.travix.medusa.busyflights.service;

import java.util.List;

import com.travix.medusa.busyflights.domain.Supplier;

public interface SupplierLoaderService {
	List<Supplier> getActiveFlightSuppliers();
}
