package com.frs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingSummaryDTO {

	private String bookingId;
	private String lastName;
	private String departure;

}
