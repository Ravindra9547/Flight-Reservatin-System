package com.frs.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frs.entity.Airport;
import com.frs.exception.AirportNotFoundException;
import com.frs.repository.AirportRepository;
import com.frs.service.AirportService;

@Service
@Transactional(readOnly = true)
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportRepository airportRepository;

	@Override
	public Airport getAirportById(String airportId) {
		return airportRepository.findById(airportId).orElseThrow(() -> new AirportNotFoundException(airportId));
	}

	@Override
	public List<Airport> getAllAirports() {
		return airportRepository.findAll();
	}

}
