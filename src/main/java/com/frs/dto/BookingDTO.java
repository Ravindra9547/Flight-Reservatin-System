package com.frs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {

	private String id;
	private PassengerDTO passenger;
	private List<FlightDTO> flights;

}
