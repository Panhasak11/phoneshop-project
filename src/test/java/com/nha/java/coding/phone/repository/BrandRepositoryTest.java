package com.nha.java.coding.phone.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nha.java.coding.phone.entity.Brand;

@DataJpaTest
public class BrandRepositoryTest {

	private BrandRepository brandRepository;

	@Autowired
    public BrandRepositoryTest(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

 
	@Test
	public void testFindByNameLike() {
//		//give
		Brand brand = new Brand();
		brand.setName("Apple");
		
		brandRepository.save(brand);
		
		//when
		List<Brand> brands = brandRepository.findByNameLike("%A%");
		
		//then
		assertEquals(1, brands.size());
		assertEquals("Apple", brands.get(0).getName());
		assertEquals(1, brands.get(0).getBrandId());
	}
}
