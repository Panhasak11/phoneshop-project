package com.nha.java.coding.phone.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.exception.ResourceNotFound;
import com.nha.java.coding.phone.repository.BrandRepository;
import com.nha.java.coding.phone.service.imp.BrandServiceImp;
import com.nha.java.coding.phone.specification.BrandSpec;
import com.nha.java.coding.phone.util.PageUtil;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

	@Mock
	private BrandRepository brandRepository;
	
	private BrandService brandService;
	
	@BeforeEach
	void setUp() {
		brandService = new BrandServiceImp(brandRepository);
	} 
	
	@Test
	public void testCreate() {
		//given
		Brand brand = new Brand();
		brand.setName("Samsung");
		
		//when 
		brandService.create(brand);

		//then
		verify(brandRepository, times(1)).save(brand);
	}
	
	@Test
	public void testGetByIdSuccess() {
		//given
		Brand brand = new Brand();
		brand.setName("Apple");
		brand.setBrandId(Long.valueOf(1));
		
		//when
		when(brandRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(brand));
	 	Brand brandReturn =brandService.getById(Long.valueOf(1));
	 	
	 	//then
	 	assertEquals(1, brandReturn.getBrandId());
	 	assertEquals("Apple", brandReturn.getName());
	}
	
	
	@Test
	public void testGetByIdThrow() {
		
		when(brandRepository.findById(Long.valueOf(2))).thenReturn(Optional.empty());
		assertThatThrownBy(() -> brandService.getById(Long.valueOf(2)))
			.isInstanceOfAny(ResourceNotFound.class)
			.hasMessage("Brand with id 2 not found");
		
	}
	
	@Test
	public void testUpdateById() {
		//given
		Long brandId = 1L;
		Brand oldBrand = new Brand();
		oldBrand.setBrandId(brandId);
		oldBrand.setName("Apple");
		
		Brand newBrand = new Brand();
		newBrand.setName("Samsung");
		
		//when
		when(brandRepository.findById(1L)).thenReturn(Optional.of(oldBrand));
		when(brandRepository.save(any(Brand.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));
		
		Brand updateBrand = brandService.updateById(brandId, newBrand);
		
		//then
		assertNotNull(updateBrand);
		assertEquals("Samsung", updateBrand.getName());
		verify(brandRepository,times(1)).findById(brandId);
		verify(brandRepository,times(1)).save(oldBrand);
		
	}
	
	@Test
	public void testGetByName() {
		//given
		String findBrand = "Apple";
		List<Brand> brandList = List.of(
				new Brand(1L,"Nike"),new Brand(2L,"Apple")
			);
		when(brandRepository.findByNameLike("%"+findBrand+"%")).thenReturn(brandList);
		
		List<Brand> brands = brandService.getBrandName(findBrand);
		
		assertNotNull(brands);
		assertEquals(2, brands.size());
		assertTrue(brands.stream()
				.anyMatch(b -> b.getName().equals("Apple")));
		verify(brandRepository,times(1)).findByNameLike("%"+findBrand+"%");
	}
	
	@Test
	public void testGetAllBrand() {
		//given 
		List<Brand> brandList = List.of(new Brand(1L,"Apple"),new Brand(2L,"Samsung"));
		when(brandRepository.findAll()).thenReturn(brandList);
		
		//when
		List<Brand> result = brandService.getAllBrand();
		
		//then
		assertEquals(2, result.size());
		assertEquals("Apple", result.get(0).getName());
		assertEquals("Samsung", result.get(1).getName());
		assertEquals(1, result.get(0).getBrandId());
		assertEquals(2, result.get(1).getBrandId());
	}
	
	
	//test get brand specification 
	
	@Test
	public void testGetBrandsNameFilter() {
		Map<String, String> params = new HashMap<>();
		params.put("name", "Apple");
		Pageable pageable = PageRequest.of(PageUtil.DEFAULT_PAGE_NUMBER, PageUtil.DEFAULT_PAGE_LIMIT);
		
		//mocking repository respone
		Page<Brand> brandPage = new PageImpl<>(List.of(new Brand(1L,"Apple")));
		 
		when(brandRepository.findAll(any(BrandSpec.class),any(Pageable.class))).thenReturn(brandPage);
		Page<Brand> brands = brandService.getBrands(params); 
		
		assertNotNull(brands);
		assertEquals(1, brands.getContent().size());
		assertEquals("Apple", brands.getContent().get(0).getName());
		verify(brandRepository,times(1)).findAll(any(BrandSpec.class),any(Pageable.class));
		
	}
	
	@Test
	public void testGetBrandsIdFilter() {
		Map<String, String> params = new HashMap<>();
		params.put("id", "1");
		Pageable pageable = PageRequest.of(PageUtil.DEFAULT_PAGE_NUMBER, PageUtil.DEFAULT_PAGE_LIMIT);
		
		Page<Brand> brandPage = new PageImpl<>(List.of(new Brand(1L,"Nika")));
		
		when(brandRepository.findAll(any(BrandSpec.class),any(Pageable.class))).thenReturn(brandPage);
		Page<Brand> brands = brandService.getBrands(params);
		
		assertNotNull(brands);
		assertEquals(1, brands.getContent().size());
		assertEquals(1L, brands.getContent().get(0).getBrandId());
		verify(brandRepository, times(1)).findAll(any(BrandSpec.class),any(Pageable.class));
	}
	
	@Test
	public void testGetBrandsPagination() {
		//given
		Map<String, String> params = new HashMap<>();
		params.put(PageUtil.PAGE_LIMIT, "5");
		params.put(PageUtil.PAGE_NUMBER, "1");
		
		Pageable pageable = PageRequest.of(0, 5);
		
		//mock page 
		Page<Brand> page = new PageImpl<>(List.of(
				new Brand(1L,"Apple"),new Brand(2L,"Samsung")));
		
		//when
		when(brandRepository.findAll(any(BrandSpec.class),eq(pageable))).thenReturn(page);
		
		Page<Brand> brands = brandService.getBrands(params);
		
		assertNotNull(brands);
		assertEquals(2, brands.getContent().size());
		verify(brandRepository, times(1)).findAll(any(BrandSpec.class),eq(pageable));
		
	}
}
