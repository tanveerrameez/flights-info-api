package com.travix.medusa.busyflights.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.exception.ServiceException;

import lombok.extern.log4j.Log4j;

/**
 * Implementation of service that provides the flights information aggregating flight results from different suppliers
 * 
 * @author tanveerrameez
 *
 */

@Service
@Log4j
public class BusyFlightDetailsServiceImpl implements BusyFlightDetailsService {

	private final SupplierLoaderService supplierLoaderService;

	private final RestTemplateService restTemplateService;

	public final Comparator<BusyFlightsResponse> busyFlightsComparator;

	@Value("${aggregrate.callable.timeout:2}")
	private long timeout;

	@Autowired
	public BusyFlightDetailsServiceImpl(final SupplierLoaderService supplierLoaderService, final RestTemplateService restTemplateService,
			final Comparator<BusyFlightsResponse> busyFlightsComparator) {
		this.supplierLoaderService = supplierLoaderService;
		this.restTemplateService = restTemplateService;
		this.busyFlightsComparator = busyFlightsComparator;
	}

	@Override
	public Set<BusyFlightsResponse> getFlights(final BusyFlightsRequest request) throws ServiceException {

		log.debug("getFlights invoked with BusyFlightsRequest request:" + request.toString());

		ExecutorService executorService = null;
		// The set of response should be sorted by fare (ascending)
		SortedSet<BusyFlightsResponse> response = new TreeSet<>(busyFlightsComparator);// Comparator.comparing(BusyFlightsResponse::getFare));
		try {
			List<Supplier> suppliers = supplierLoaderService.getActiveFlightSuppliers();
			if (!CollectionUtils.isEmpty(suppliers)) {
				// set pool size equal to number of suppliers
				executorService = Executors.newFixedThreadPool(suppliers.size());// .newCachedThreadPool();
				Collection<Callable<List<BusyFlightsResponse>>> callables = new ArrayList<>();

				// iterate through supplier and create Callables
				suppliers.forEach(supplier -> callables.add(createCallable(supplier, request)));

				try {
					List<Future<List<BusyFlightsResponse>>> taskFutureList = executorService.invokeAll(callables);
					// aggregate flight results
					for (Future<List<BusyFlightsResponse>> future : taskFutureList) {
						try {

							response.addAll(future.get(timeout, TimeUnit.SECONDS));

						} catch (Exception ex) {
							future.cancel(true);
							/*
							 * this should be logged as this error needs to be investigated. The response will continue with the flights that have
							 * been successfully retrieved.
							 * 
							 * @todo: The information about which callable failed should be recorded with information like suppler, request, type of
							 * exception etc
							 */
							log.error("Exception while aggregating flight details", ex);
						}
					}

				} catch (InterruptedException e) {
					log.error("Interrupted Exception while invoking all callables", e);
					throw new ServiceException("Interrupted Exception occured");
				}
			}

		} finally {
			if (executorService != null) {
				executorService.shutdown();
			}
		}
		return response;

	}

	private <T, U> Callable createCallable(Supplier<T, U> supplier, BusyFlightsRequest request) {
		return new GetFlightsFromSupplierTask<T, U>(supplier, restTemplateService, request);
	}

}
