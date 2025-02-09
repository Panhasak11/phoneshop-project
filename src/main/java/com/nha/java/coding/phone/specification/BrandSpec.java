package com.nha.java.coding.phone.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.nha.java.coding.phone.entity.Brand;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class BrandSpec implements Specification<Brand>{

	private final BrandFilter brandFilter;
	
	List<Predicate> predicates = new ArrayList<>();
	
	@Override
	public Predicate toPredicate(Root<Brand> brand, CriteriaQuery<?> query, CriteriaBuilder cb) {

		if(brandFilter.getName() != null) {
			Predicate name = cb.like(cb.upper(brand.get("name")),"%" + brandFilter.getName().toUpperCase()+"%");
			predicates.add(name);
		}
		if(brandFilter.getId() != null) {
			Predicate id = brand.get("id").in(brandFilter.getId());
			predicates.add(id);
		}
		
		return cb.and(predicates.toArray(Predicate[]::new));
	}

}
