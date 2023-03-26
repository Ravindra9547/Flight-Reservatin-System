package com.frs.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frs.dto.BookingDTO;
import com.frs.dto.BookingSummaryDTO;
import com.frs.dto.ToBookingDTOConverter;
import com.frs.entity.Flight;
import com.frs.entity.Booking;
import com.frs.exception.BookingNotFoundException;
import com.frs.service.BookingService;

@RestController
@RequestMapping("bookings")
public class BookingController {

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ToBookingDTOConverter toBookingDTOConverter;

	@GetMapping("/{bookingId}")
	public @ResponseBody BookingDTO getBookingById(@PathVariable String bookingId) {

		logger.info("Finding booking by id ... " + bookingId);
		return toBookingDTOConverter.convert(bookingService.getBooking(bookingId));
	}

	@GetMapping
	public @ResponseBody List<BookingSummaryDTO> getBookings(
			@RequestParam(required = false, name = "uid") String passengerId, @RequestParam(required = false, name = "multi-flights", defaultValue = "false") boolean multiFlights) {

		logger.info("Finding booking by passengerId ... " + passengerId);

		List<Booking> bookings = null;
		
		if(!StringUtils.isEmpty(passengerId)) {
			
			if(multiFlights) {
				bookings = bookingService.getAllMultiFlightBookingsByPassenger(passengerId);
			} else {
				bookings = bookingService.getAllBookingsByPassenger(passengerId);
			}
		} else {
			throw new BookingNotFoundException(null);
		}
	
		List<BookingSummaryDTO> bookingModels = new ArrayList<>(bookings.size());
		bookings.stream().forEach(booking -> {
			bookingModels.add(new BookingSummaryDTO(booking.getId(), booking.getPassenger().getLastName(),
					((Flight) booking.getFlights().toArray()[0]).getDeparture()));
		});
		return bookingModels;
	}

	private List<BookingSummaryDTO> getBookingsByPassengerId(String passengerId) {

		logger.info("Finding booking by passengerId ... " + passengerId);

		List<Booking> bookings = bookingService.getAllBookingsByPassenger(passengerId);
		List<BookingSummaryDTO> bookingModels = new ArrayList<>(bookings.size());
		bookings.stream().forEach(booking -> {
			bookingModels.add(new BookingSummaryDTO(booking.getId(), booking.getPassenger().getLastName(),
					((Flight) booking.getFlights().toArray()[0]).getDeparture()));
		});
		return bookingModels;
	}
}
