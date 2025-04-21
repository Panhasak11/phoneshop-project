package com.nha.java.coding.phone.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SaleDTO {
	
	private List<ProductSoldDTO> products;
	private LocalDateTime saleDate;
}
