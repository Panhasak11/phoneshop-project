package com.nha.java.coding.phone.service.imp;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.dto.ProductImportDTO;
import com.nha.java.coding.phone.entity.Product;
import com.nha.java.coding.phone.entity.ProductImportHistory;
import com.nha.java.coding.phone.exception.ResourceNotFoundException;
import com.nha.java.coding.phone.mapper.ProductMapper;
import com.nha.java.coding.phone.repository.ProductImportHistoryRepository;
import com.nha.java.coding.phone.repository.ProductRepository;
import com.nha.java.coding.phone.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ProductImportHistoryRepository importHistoryRepository;
	
	
	@Override
	public Product create(Product product) {
		String name = "%s %s".formatted(product.getModel().getName(), product.getColor().getName());
		product.setName(name);
		return productRepository.save(product);
	}

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", id));
	}

	@Override
	public void importProduct(ProductImportDTO importDTO) {
		//update product available unit
		Product product = findById(importDTO.getProductId());
		Integer availabelUnit = 0;
		if(product.getAvailableUnit()!= null) {
			availabelUnit = product.getAvailableUnit();
		}
		product.setAvailableUnit(availabelUnit + importDTO.getImportUnit());
		productRepository.save(product);
		
		
		//save import history
		ProductImportHistory importHistory = productMapper.toProductImportHistory(importDTO, product);
		importHistoryRepository.save(importHistory);
		
	}

	@Override
	public void setSalePrice(Long id, BigDecimal price) {
		Product product = findById(id);
		product.setSalePrice(price);
		productRepository.save(product);
	}


}
