package com.nha.java.coding.phone.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.ExpenseReportDTO;
import com.nha.java.coding.phone.dto.ProductReportDTO;
import com.nha.java.coding.phone.service.ReportServie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("report")
public class ReportController {
	
	private final ReportServie reportServie;
	
	@GetMapping("sales/{startDate}/{endDate}")
	public ResponseEntity<?> report(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate){
		List<ProductReportDTO> productReport = reportServie.getProductReport(startDate, endDate);
		
		return ResponseEntity.ok(productReport);
	}
	
	@GetMapping("expense/{startDate}/{endDate}")
	public ResponseEntity<?> expenseReport(@DateTimeFormat(pattern = "yyyy-MM-dd") 
							@PathVariable("startDate") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd")
							@PathVariable("endDate") LocalDate endDate){
		List<ExpenseReportDTO> expenseReport = reportServie.getExpenseReport(startDate, endDate);
		return ResponseEntity.ok(expenseReport);
	}
	 
}
