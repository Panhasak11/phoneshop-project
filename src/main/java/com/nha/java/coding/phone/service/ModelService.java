package com.nha.java.coding.phone.service;

import java.util.List;

import com.nha.java.coding.phone.entity.Model;

public interface ModelService {

	Model create(Model model);
	Model findById(Long id);
	Model update(Long modelId, Model updateId);
	List<Model> getByBrand(Long brandId);
	List<Model> getAllModel();
}
