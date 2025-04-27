package com.nha.java.coding.phone.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.nha.java.coding.phone.entity.ProductImportHistory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductImportHistorySpec implements Specification<ProductImportHistory>{

	private ProductImportHistoryFilter importHistoryFilter;
	
	@Override
	public Predicate toPredicate(Root<ProductImportHistory> importHistory, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if(Objects.nonNull(importHistoryFilter.getStartDate())) {
			cb.greaterThanOrEqualTo(importHistory.get("dateImport"), importHistoryFilter.getStartDate());
		}
		if(Objects.nonNull(importHistoryFilter.getEndDate())) {
			cb.greaterThanOrEqualTo(importHistory.get("dateImport"), importHistoryFilter.getEndDate());
		}
		Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
		return predicate;
	}

}
