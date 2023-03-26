package com.frs.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.frs.entity.Flight;
import com.frs.entity.Booking;

@Component
public class ToBookingDTOMinimalConverter implements Converter<Booking, BookingSummaryDTO> {

	@Override
	public BookingSummaryDTO convert(Booking source) {
		Flight flight = CollectionUtils.isEmpty(source.getFlights()) ? new Flight()
				: (Flight) source.getFlights().toArray()[0];
		return new BookingSummaryDTO(source.getId(), source.getPassenger().getLastName(),
				flight.getDeparture());
	}

}
