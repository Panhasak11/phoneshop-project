package com.nha.java.coding.phone.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.entity.Brand;
import com.nha.java.coding.phone.exception.ResourceNotFound;
import com.nha.java.coding.phone.repository.BrandRepository;
import com.nha.java.coding.phone.service.BrandService;
import com.nha.java.coding.phone.specification.BrandFilter;
import com.nha.java.coding.phone.specification.BrandSpec;
import com.nha.java.coding.phone.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImp implements BrandService{
 
	private final BrandRepository brandRepository;
	
	@Override
	public Brand create(Brand brand) {
		return brandRepository.save(brand);
	}

	@Override
	public Brand getById(Long brandId) {
		return brandRepository.findById(brandId)
				.orElseThrow(() -> new ResourceNotFound("Brand", brandId));
	}

	@Override
	public Brand updateById(Long id, Brand updateBrand) {
		Brand brand = getById(id);
		brand.setName(updateBrand.getName());
		return brandRepository.save(brand);
	}

	@Override
	public List<Brand> getBrandName(String name) {
		return brandRepository.findByNameLike("%"+name+"%");
	}
	
	//get all brand simple
	@Override
	public List<Brand> getAllBrand() {
		List<Brand> list = brandRepository.findAll();
		return list;
	}

	//get all brand with specification and page filter
	@Override
	public Page<Brand> getBrands(Map<String, String> param) {

		BrandFilter filter = new BrandFilter();
		
		if(param.containsKey("name")) {
			String name = param.get("name");	
			filter.setName(name);
		}
		
		if(param.containsKey("id")) {
			String id = param.get("id");
			filter.setId(Long.parseLong(id));
		}
		
		int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
		if(param.containsKey(PageUtil.PAGE_LIMIT)) {
			pageLimit = Integer.parseInt(param.get(PageUtil.PAGE_LIMIT));
		}
		
		int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
		if(param.containsKey(PageUtil.PAGE_NUMBER)) {
			pageNumber = Integer.parseInt(param.get(PageUtil.PAGE_NUMBER));
		}
		
		BrandSpec brandSpec = new BrandSpec(filter);
		
		Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);
		
		Page<Brand> pageBrand = brandRepository.findAll(brandSpec,pageable);
		
		return pageBrand;
	}

	@Override
	public void deleteById(Long id) {
		Brand brand = getById(id);
		brandRepository.delete(brand);
	}
	
}
