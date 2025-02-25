package com.nha.java.coding.phone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.nha.java.coding.phone.dto.ModelDTO;
import com.nha.java.coding.phone.dto.ModelUpdateDTO;
import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.mapper.ModelEntityMapper;
import com.nha.java.coding.phone.service.ModelService;

@ExtendWith(MockitoExtension.class)
public class ModelControllerTest {

	private ModelController modelController;
	
	@Mock
	private ModelService modelService;
	@Mock
	private ModelEntityMapper modelEntityMapper;
	
	@BeforeEach
	void setUp() {
		modelController = new ModelController(modelService, modelEntityMapper);
	}
	
	@Test
	public void testCreateModel() {
		ModelDTO modelDTO = new ModelDTO();
		
		ResponseEntity<?> respone = modelController.creatModel(modelDTO);
		
		assertNotNull(respone);
		assertEquals(200, respone.getStatusCodeValue());
	}
	
	@Test
	public void testGetModelId() {
		Model model = new Model();
		model.setId(1L);
		model.setName("Iphone");
		
		when(modelService.findById(1L)).thenReturn(model);
		
		ResponseEntity<?> respone = modelController.getModelId(1L);
		
		assertNotNull(respone);
		assertEquals(200, respone.getStatusCodeValue());
		verify(modelService,times(1)).findById(1L);
	}
	
	@Test
	public void testUpdateModel() {
		Long id = 1L;
        ModelUpdateDTO updateDTO = new ModelUpdateDTO();
        Model model = new Model();
        Model updatedModel = new Model();
        ModelDTO expectedDTO = new ModelDTO();

        when(modelEntityMapper.toModel(updateDTO)).thenReturn(model);
        when(modelService.update(id, model)).thenReturn(updatedModel);
        when(modelEntityMapper.toModelDTO(updatedModel)).thenReturn(expectedDTO);

        ResponseEntity<?> response = modelController.update(id, updateDTO);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDTO, response.getBody());
	}
	
	@Test
	public void testGetAllBrand() {
		
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		
		List<Model> models =  List.of(
				new Model(1L, "Iphone", brand),
				new Model(2L, "Ipod",brand));
		
		System.out.println(models);
		
		when(modelService.getAllModel()).thenReturn(models);
		
		ResponseEntity<?> result = modelController.getAllBrand();
		
		assertNotNull(result);
		assertEquals(200, result.getStatusCodeValue());
	}
}
