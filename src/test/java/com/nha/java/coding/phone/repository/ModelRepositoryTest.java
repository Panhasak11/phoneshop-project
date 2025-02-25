package com.nha.java.coding.phone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.entity.Model;

@ExtendWith(MockitoExtension.class)
public class ModelRepositoryTest {

	@Mock
	private ModelRepository modelRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindByBrandId() {
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		
		Model model = new Model();
		model.setBrand(brand);
		model.setId(1L);
		model.setName("Iphone 11");
		
		when(modelRepository.findByBrandId(brand.getId()))
			.thenReturn(
					Stream.of(model)
					.collect(Collectors.toList()));
		
		List<Model> brandList = modelRepository.findByBrandId(brand.getId());
		
		assertEquals(1, brandList.size());
		assertEquals("Iphone 11", brandList.get(0).getName());
		assertEquals("Apple", brandList.get(0).getBrand().getName());
		assertEquals(1, brandList.get(0).getId());
		assertEquals(1, brandList.get(0).getBrand().getId());	
	}
}
