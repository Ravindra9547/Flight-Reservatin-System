package com.frs.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.frs.entity.Flight;
import com.frs.entity.Booking;
import com.frs.entity.Passenger;
import com.frs.exception.BookingNotFoundException;
import com.frs.exception.BookingNotFoundForPassengerException;
import com.frs.repository.BookingRepository;
import com.frs.repository.FlightRepository;
import com.frs.repository.PassengerRepository;
import com.frs.service.BookingService;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {
	private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	private BookingRepository bookingRepositoty;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public Booking getBooking(String bookingId) {
		return bookingRepositoty.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
	}

	@Override
	public List<Booking> getAllBookingsByPassenger(String passengerId) {
		List<Booking> bookings = bookingRepositoty.findByPassengerId(passengerId);
		if (CollectionUtils.isEmpty(bookings))
			throw new BookingNotFoundForPassengerException(passengerId);
		return bookings;
	}

	@Override
	@Transactional
	public void createSampleBookings() {
		List<Passenger> allPassengers = passengerRepository.findAll();
		final Random flightSelector = new Random(1);
		int[] passCounter = new int[1];
		allPassengers.stream().forEach(passenger -> {
			createRandomBooking(passenger, flightSelector);
			if (passCounter[0] % 3 == 0) {
				createRandomBooking(passenger, flightSelector);
			}
			passCounter[0]++;
		});
	}

	private void createRandomBooking(Passenger passenger, Random flightSelector) {
		String flightID = "FL-" + (flightSelector.nextInt(19) + 1);
		logger.debug("----------------- FL-ID = " + flightID);
		Flight flight = flightRepository.findById(flightID).orElse(null);
		logger.debug("---------------- Flight = " + flight);

		List<Flight> nextFlights = flightRepository.findByDepartureAndDepartureDateGreaterThan(flight.getArrival(),
				flight.getArrivalDate());
		Flight nextFlight = CollectionUtils.isEmpty(nextFlights) ? null : nextFlights.get(0);
		logger.debug("--------------- nextFlight = " + nextFlight);

		Booking booking = new Booking();
		booking.setPassenger(passenger);

		Set<Flight> flights = new HashSet<>(2);
		flights.add(flight);
		if (CollectionUtils.isEmpty(flight.getBookings())) {
			flight.setBookings(new HashSet<>(2));
		}
		flight.getBookings().add(booking);
		if (nextFlight != null) {
			flights.add(nextFlight);
			if (CollectionUtils.isEmpty(nextFlight.getBookings())) {
				nextFlight.setBookings(new HashSet<>(2));
			}
			nextFlight.getBookings().add(booking);
		}
		booking.setFlights(flights);
		bookingRepositoty.save(booking);
		flightRepository.save(flight);
		flightRepository.save(flight);
		if (nextFlight != null) {
			flightRepository.save(nextFlight);
		}
	}

	@Override
	public List<Booking> getAllMultiFlightBookingsByPassenger(String passengerId) {
		List<Booking> bookings = bookingRepositoty.findByPassengerId(passengerId);
		return bookings.stream().filter(booking -> booking.getFlights().size() > 1).collect(Collectors.toList());
	}

	@Override
	public List<Booking> getAllMultiFlightBookings() {
		List<Booking> bookings = bookingRepositoty.findAll();
		return bookings;
	}

}
