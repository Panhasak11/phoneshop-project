package com.nha.java.coding.phone.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nha.java.coding.phone.dto.ModelDTO;
import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.exception.ResourceNotFoundException;
import com.nha.java.coding.phone.repository.ModelRepository;
import com.nha.java.coding.phone.service.imp.ModelServiceImp;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

	@Mock
	private ModelRepository modelRepository;
	
	private ModelService modelService;
	
	@BeforeEach
	void setUp() {
		modelService = new ModelServiceImp(modelRepository);
	}
	
	@Test
	public void testCreate() {
		Brand brand = new Brand();
		brand.setName("Apple");
		
		
		Model model = new Model();
		model.setBrand(brand);
		model.setId(1L);
		model.setName("Iphone X");
		
		when(modelRepository.save(any(Model.class))).thenReturn(model);
		
		Model saveModel = modelService.create(model);
//		System.out.println(saveModel);
		
		assertEquals(1, saveModel.getId());
		assertEquals("Iphone X", saveModel.getName());
		verify(modelRepository, times(1)).save(model);
	}
	
	@Test
	public void testFindByIdSuccess() {
		Model model = new Model();
		model.setId(1L);
		model.setName("Apple");
		
		when(modelRepository.findById(model.getId())).thenReturn(Optional.of(model));
		
		Model result = modelService.findById(model.getId());
		
		assertEquals(1, result.getId());
		assertEquals("Apple", result.getName());
	}
	
	@Test
	public void testFindByIdThrow() {
		Model model = new Model();
		model.setId(1L);
		model.setName("Apple");
		
		when(modelRepository.findById(2L)).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> modelService.findById(2L))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessage("Model with id 2 not found");
	}
	
	@Test
	public void testUpdateModel() {
		Long modelId = 1L;
		Model model = new Model();
		model.setId(1L);
		model.setName("Iphone");
		
		Model model2 = new Model();
		model2.setName("IPhone Xs");
		
		model.setName(model2.getName());
//		System.out.println(model);
		
		when(modelRepository.findById(modelId)).thenReturn(Optional.of(model));
		when(modelRepository.save(model)).thenReturn(model);
		
		Model update = modelService.update(modelId, model2);
		
		assertEquals("IPhone Xs", update.getName());
		verify(modelRepository, times(1)).findById(modelId);
		verify(modelRepository, times(1)).save(update);
	}
	
	@Test
	public void testGetByBrand() {
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		
		Model model = new Model();
		model.setBrand(brand);
		model.setId(1L);
		model.setName("Iphone x");
		
		List<Model> models = new ArrayList<>();
		models.add(model);
		
		when(modelRepository.findByBrandId(brand.getId())).thenReturn(models);
		
		List<Model> modelList = modelService.getByBrand(brand.getId());
		
		assertEquals(1, modelList.size());
		assertEquals(1, modelList.get(0).getId());
		assertEquals("Iphone x", modelList.get(0).getName());
		assertEquals("Apple", modelList.get(0).getBrand().getName());
	}
	
	@Test
	public void testGetAllModel() {
		
		Model model1 = new Model();
		model1.setId(1L);
		model1.setName("Iphone");
		
		Model model2 = new Model();
		model2.setId(2L);
		model2.setName("Galaxy");
		
		List<Model> models = List.of(model1,model2);
		
		when(modelRepository.findAll()).thenReturn(models);
		
		List<Model> listModel = modelService.getAllModel();
		
		assertEquals(2, listModel.size());
		assertEquals(1, listModel.get(0).getId());
		assertEquals("Iphone", listModel.get(0).getName());
		verify(modelRepository, times(1)).findAll();
	}
	
}
