package com.frs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.frs.entity.Booking;

@Transactional
public interface BookingRepository extends JpaRepository<Booking, String> {

	List<Booking> findByPassengerId(String passengerId);
}
