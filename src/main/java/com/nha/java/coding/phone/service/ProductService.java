package com.nha.java.coding.phone.service;

import java.math.BigDecimal;

import com.nha.java.coding.phone.dto.ProductImportDTO;
import com.nha.java.coding.phone.entity.Product;

public interface ProductService {

	Product create(Product product);
	Product findById(Long id);
	void importProduct(ProductImportDTO importDTO);
	void setSalePrice(Long id, BigDecimal price);
}
