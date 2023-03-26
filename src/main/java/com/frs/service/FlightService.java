package com.frs.service;

import java.util.List;

import com.frs.entity.Flight;

public interface FlightService {
	
	Flight getFlightById(String flightId);

	List<Flight> getAllFlights();
}
