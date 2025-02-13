package com.nha.java.coding.phone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.nha.java.coding.phone.dto.BrandDTO;
import com.nha.java.coding.phone.dto.PageDTO;
import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.mapper.BrandMapper;
import com.nha.java.coding.phone.repository.BrandRepository;
import com.nha.java.coding.phone.service.BrandService;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {

	@Mock
	private BrandService brandService;
	@Mock
	private BrandRepository brandRepository;
	
	private BrandController brandController;
		
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		brandController = new BrandController(brandService);
	}
	
	
	@Test
	public void testCreate() {
		//given
		Brand brand = new Brand();
		brand.setName("Apple");
		
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Apple");
		
		//when
		when(brandService.create(any(Brand.class))).thenReturn(brand);
		
		ResponseEntity<?> response = brandController.create(brandDTO);

		//then
	    assertNotNull(response);
	    assertEquals(200, response.getStatusCodeValue());
	    assertEquals(brandDTO, response.getBody());
	}
	
	@Test
	public void testGetBrand() {
		//given 
		Brand brand = new Brand(1L,"Apple");
		BrandDTO brandDTO = new BrandDTO(1L,"Apple");
		
		//when
		when(brandService.getById(1L)).thenReturn(brand);
		
		ResponseEntity<?> respone = brandController.getBrand(brand.getBrandId());
		
		//then
		assertNotNull(respone);
		assertEquals(200, respone.getStatusCodeValue());		
	}
	
	@Test
	public void testUpdateBrandById() {
		//given
//		Long brandId = 1L;
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setName("Apple");
		
		Brand updateBrand = new Brand();
		updateBrand.setBrandId(brandDTO.getBrandId());
		updateBrand.setName("Apple");
		
	    when(BrandMapper.INSTANCE.toBrand(brandDTO)).thenReturn(updateBrand);
		when(brandService.updateById(brandDTO.getBrandId(), updateBrand)).thenReturn(updateBrand);

		ResponseEntity<?> responseEntity = brandController.updateBrand(brandDTO.getBrandId(), brandDTO);
		
		assertNotNull(responseEntity);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(brandDTO, responseEntity.getBody());
//		verify(brandService , times(1)).updateById(brandId, updateBrand);
 	}
	
	@Test
	public void testGetBrandByName() {
		Brand brand = new Brand();
		brand.setName("Apple");
		
		List<Brand> brandList = new ArrayList<>();
		brandList.add(brand);
		
		when(brandService.getBrandName(brand.getName())).thenReturn(brandList);
		
		ResponseEntity<?> respone = brandController.getBrandName(brand.getName());
		
		assertNotNull(respone);
		assertEquals(200, respone.getStatusCodeValue());
		
		verify(brandService, times(1)).getBrandName(brand.getName());
	}
	
	@Test
	public void testGetBrandByPage() {

		List<Brand> brandList = List.of(
				new Brand(1L, "Apple"),
				new Brand(1L, "Samsung"));
		
		Map<String, String> param = new HashMap<>();
		param.put("page", "0");
		param.put("size", "5");
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<Brand> brandPage = new PageImpl<>(brandList, pageable, brandList.size());
		
		when(brandService.getBrands(param)).thenReturn(brandPage);
		
		ResponseEntity<?> respone = brandController.getBrandByPage(param);
		
		assertEquals(200, respone.getStatusCodeValue());
		assertTrue(respone.getBody() instanceof PageDTO);
	}
	
	@Test
	public void testDeleteById() {
		Brand brand = new Brand(1L,"Apple");
		
		ResponseEntity<?> responseEntity = brandController.deleteById(brand.getBrandId());
		
		verify(brandService, times(1)).deleteById(brand.getBrandId());
		assertEquals(200, responseEntity.getStatusCodeValue());
	}
} 
