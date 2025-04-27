package com.nha.java.coding.phone.specification;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SaleDetailFilter {

	private LocalDate startDate;
	private LocalDate endDate;
}
