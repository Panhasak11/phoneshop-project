package com.nha.java.coding.phone.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
	private String name;
	private Integer available_unit;
	private BigDecimal salePrice;
	private Long modelId;
	private Long colorId;
}
