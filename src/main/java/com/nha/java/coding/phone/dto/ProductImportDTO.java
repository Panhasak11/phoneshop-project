package com.nha.java.coding.phone.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductImportDTO {

	//to catch this annotation message we need to add validation dependency 
	//and configure exception to get this message
	
	@NotNull(message = "Product id can't be null")
	private Long productId;
	
	@NotNull(message = "Date import can't be null")
	private LocalDateTime dateImport;
	
	@Min(value = 1, message = "Import unit must be grater than 0")
	private Integer importUnit;
	
	@DecimalMin(value = "0.000001", message = "Price must be greater than 0")
	private BigDecimal unitPrice;
}
