package com.nha.java.coding.phone.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.exception.ResourceNotFoundException;
import com.nha.java.coding.phone.repository.ModelRepository;
import com.nha.java.coding.phone.service.ModelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModelServiceImp implements ModelService{

	private final ModelRepository modelRepository;
	
	@Override
	public Model create(Model model) {
		return modelRepository.save(model);
	}

	@Override
	public Model findById(Long id) {
		return modelRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Model", id));
	}

	@Override
	public Model update(Long modelId, Model updateModel) {
		Model model = findById(modelId);
		model.setName(updateModel.getName());
		model.setBrand(model.getBrand());
		return modelRepository.save(model);
	}

	@Override
	public List<Model> getByBrand(Long brandId) {
		return modelRepository.findByBrandId(brandId);
	}

	@Override
	public List<Model> getAllModel() {
		return modelRepository.findAll();
	}

}
