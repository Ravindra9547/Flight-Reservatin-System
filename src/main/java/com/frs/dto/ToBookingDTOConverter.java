package com.frs.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.frs.entity.Booking;

@Component
public class ToBookingDTOConverter implements Converter<Booking, BookingDTO> {

	@Override
	public BookingDTO convert(Booking source) {
		
		List<FlightDTO> flights = new ArrayList<>();

		if (!CollectionUtils.isEmpty(source.getFlights())) {
			source.getFlights().stream().forEach(flight -> {
				flights.add(new FlightDTO(flight.getDeparture(), flight.getArrival(),
						flight.getDepartureDate().toString(), flight.getArrivalDate().toString()));
			});
		}

		return new BookingDTO(source.getId(), new PassengerDTO(source.getPassenger().getFirstName(),
				source.getPassenger().getLastName(), source.getPassenger().getEmail()), flights);
	}

}
