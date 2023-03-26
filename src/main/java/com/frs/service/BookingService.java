package com.frs.service;

import java.util.List;

import com.frs.entity.Booking;

public interface BookingService {
	
	Booking getBooking(String bookingId);
	
	List<Booking> getAllBookingsByPassenger(String passengerId);

	List<Booking> getAllMultiFlightBookingsByPassenger(String passengerId);

	List<Booking> getAllMultiFlightBookings();
	
	void createSampleBookings();

}
