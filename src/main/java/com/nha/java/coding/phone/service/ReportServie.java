package com.nha.java.coding.phone.service;

import java.time.LocalDate;
import java.util.List;

import com.nha.java.coding.phone.dto.ExpenseReportDTO;
import com.nha.java.coding.phone.dto.ProductReportDTO;

public interface ReportServie {

	
	List<ProductReportDTO> getProductReport(LocalDate startDate, LocalDate endDate);
	List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate);
}
