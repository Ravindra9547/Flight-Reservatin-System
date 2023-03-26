package com.frs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.frs.entity.Airport;

@Transactional
public interface AirportRepository extends JpaRepository<Airport, String> {
	
}
