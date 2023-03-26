package com.frs.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frs.entity.Flight;
import com.frs.exception.FlightNotFoundException;
import com.frs.repository.FlightRepository;
import com.frs.service.FlightService;

@Service
@Transactional(readOnly = true)
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepo;

	@Override
	public Flight getFlightById(String flightId) {
		return flightRepo.findById(flightId).orElseThrow(() -> new FlightNotFoundException(flightId));
	}

	@Override
	public List<Flight> getAllFlights() {
		return flightRepo.findAll();
	}

}
