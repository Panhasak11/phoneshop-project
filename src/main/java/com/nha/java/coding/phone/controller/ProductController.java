package com.nha.java.coding.phone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.PriceDTO;
import com.nha.java.coding.phone.dto.ProductDTO;
import com.nha.java.coding.phone.dto.ProductImportDTO;
import com.nha.java.coding.phone.entity.Product;
import com.nha.java.coding.phone.mapper.ProductMapper;
import com.nha.java.coding.phone.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {

	
	private final ProductService productService;
	private final ProductMapper productMapper;
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
		Product product = productMapper.toProduct(productDTO);
		product = productService.create(product);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping("import")
	public ResponseEntity<?> importProduct(@RequestBody @Valid ProductImportDTO importDTO){
		productService.importProduct(importDTO);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("{id}/salePrice")
	public ResponseEntity<?> setPrice(@PathVariable Long id, @RequestBody PriceDTO priceDTO){
		productService.setSalePrice(id, priceDTO.getPrice());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		Product product = productService.findById(id);
		return ResponseEntity.ok(productMapper.toProductDTO(product));
	}
}
