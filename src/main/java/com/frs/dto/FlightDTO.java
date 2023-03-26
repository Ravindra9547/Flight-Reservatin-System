package com.frs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightDTO {
	
	private String departure;
	private String arrival;
	private String departureDate;
	private String arrivalDate;

}
