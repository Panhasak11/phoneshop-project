package com.nha.java.coding.phone.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.nha.java.coding.phone.entity.Brand;

public interface BrandService {

	Brand create(Brand brand);
	Brand getById(Long brandId);
	Brand updateById(Long id,Brand brandId);
	List<Brand> getBrandName(String name);
	List<Brand> getAllBrand();
	Page<Brand> getBrands(Map<String, String> param);
	void deleteById(Long id);
}
