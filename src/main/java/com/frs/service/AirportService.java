package com.frs.service;

import java.util.List;

import com.frs.entity.Airport;

public interface AirportService {
	Airport getAirportById(String airportId);
	List<Airport> getAllAirports();
}
