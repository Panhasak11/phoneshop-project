package com.nha.java.coding.phone.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.BrandDTO;
import com.nha.java.coding.phone.dto.ModelDTO;
import com.nha.java.coding.phone.dto.PageDTO;
import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.mapper.BrandMapper;
import com.nha.java.coding.phone.mapper.ModelEntityMapper;
import com.nha.java.coding.phone.service.BrandService;
import com.nha.java.coding.phone.service.ModelService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("brands")
public class BrandController {
	
	private final BrandService brandService;
	private final ModelService modelService;
	private final ModelEntityMapper modelEntityMapper;
//	private final BrandMapper brandMapper;
	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
		Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
		brand = brandService.create(brand);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getBrand(@PathVariable("id") Long brandId){
		Brand brand = brandService.getById(brandId);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateBrand(@PathVariable("id") Long brandId, @RequestBody BrandDTO brandDTO){
		Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
		Brand updateBrand = brandService.updateById(brandId, brand);
		return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(updateBrand));
	}
	 
	@GetMapping("filter")
	public ResponseEntity<?> getBrandName(@RequestParam("name") String name){
		List<BrandDTO> list = brandService.getBrandName(name)
			.stream()
			.map(brand -> BrandMapper.INSTANCE.toBrandDTO(brand))
			.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	
		
//	@GetMapping
//	public ResponseEntity<?> getAllBrand(){
//		List<BrandDTO> list = brandService.getAllBrand()
//			.stream()
//			.map(brand -> BrandMapper.INSTANCE.toBrandDTO(brand))
//			.collect(Collectors.toList());
//		
//		return ResponseEntity.ok(list);
//	}
	
	@GetMapping
	public ResponseEntity<?> getBrandByPage(@RequestParam Map<String, String> param){
		Page<Brand> brands = brandService.getBrands(param);

		PageDTO pageDTO = new PageDTO(brands);
		
		return ResponseEntity.ok(pageDTO);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		brandService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("{id}/models")
	public ResponseEntity<?> getModelByBrand(@PathVariable("id") Long brandId){
		List<Model> brands = modelService.getByBrand(brandId);
		List<ModelDTO> list = brands.stream()
			.map(modelEntityMapper::toModelDTO)
			.toList();
		return ResponseEntity.ok(list);
	}

}
