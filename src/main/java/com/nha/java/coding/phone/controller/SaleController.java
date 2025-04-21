package com.nha.java.coding.phone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.SaleDTO;
import com.nha.java.coding.phone.service.ProductService;
import com.nha.java.coding.phone.service.SaleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("sales")
public class SaleController {

	private final ProductService productService;
	private final SaleService saleService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody SaleDTO saleDTO){
		saleService.sell(saleDTO);
		return ResponseEntity.ok().build();
	}
	
	
	@PutMapping("{saleId}/cancel")
	public ResponseEntity<?> cancelSale(@PathVariable Long saleId){
		saleService.cancelSale(saleId);
		return ResponseEntity.ok().build();
	}
}
