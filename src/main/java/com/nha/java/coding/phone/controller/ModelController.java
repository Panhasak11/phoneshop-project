package com.nha.java.coding.phone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.ModelDTO;
import com.nha.java.coding.phone.dto.ModelUpdateDTO;
import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.mapper.ModelEntityMapper;
import com.nha.java.coding.phone.service.ModelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("models")
public class ModelController {

	private final ModelService modelService;
	private final ModelEntityMapper modelEntityMapper;
	
	
	@PostMapping
	public ResponseEntity<?> creatModel(@RequestBody ModelDTO modelDTO){
		Model model = modelEntityMapper.toModel(modelDTO);
		model = modelService.create(model);
		return ResponseEntity.ok(modelEntityMapper.toModelDTO(model));
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<?> getModelId(@PathVariable("id") Long modelId){
		Model model = modelService.findById(modelId);
		return ResponseEntity.ok(modelEntityMapper.toModelDTO(model));
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ModelUpdateDTO updateDTO){
		Model model = modelEntityMapper.toModel(updateDTO);
		Model updateModel = modelService.update(id, model);
		return ResponseEntity.ok(modelEntityMapper.toModelDTO(updateModel));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllBrand(){
		List<Model> models = modelService.getAllModel();
		List<ModelDTO> modelList = models.stream()
			.map(modelEntityMapper::toModelDTO)
			.toList();
		return ResponseEntity.ok(modelList);
	}
}
