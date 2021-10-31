package com.travix.medusa.busyflights.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.Supplier;
/**
 * Implementation providing list of suppliers
 * 
 * @author tanveerrameez
 *
 */

@Service
public class SupplierLoaderServiceImpl implements SupplierLoaderService {

	private final List<Supplier> suppliers;

	@Autowired
	public SupplierLoaderServiceImpl(final List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	@Override
	public List<Supplier> getActiveFlightSuppliers() {
		return suppliers;
	}
}
