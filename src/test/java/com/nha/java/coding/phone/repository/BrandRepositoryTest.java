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

@ExtendWith(MockitoExtension.class)
public class BrandRepositoryTest {

//	@Autowired
	@Mock
	private BrandRepository brandRepository;
	
	@BeforeEach
	void setUp() {
		//it's initializes @Mock object 
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindByNameLike() {
		Brand brand1 = new Brand();
		brand1.setId(1L);
		brand1.setName("Apple");
		
		when(brandRepository.findByNameLike("%A%"))
			.thenReturn(Stream.of(brand1).collect(Collectors.toList()));
		
		List<Brand> brands = brandRepository.findByNameLike("%A%");
		
		assertEquals(1, brands.size());
		assertEquals("Apple", brands.get(0).getName());
		assertEquals(1, brands.get(0).getId());
	}
}
