package com.nha.java.coding.phone.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

import com.nha.java.coding.phone.entity.Sale;
import com.nha.java.coding.phone.entity.SaleDetail;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaleDetailSpec implements Specification<SaleDetail>{

	private SaleDetailFilter detailFilter;
	
	@Override
	public Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		Join<SaleDetail, Sale> sale = saleDetail.join("sale");
		if(Objects.nonNull(detailFilter.getStartDate())) {
			cb.greaterThanOrEqualTo(sale.get("saleDate"), detailFilter.getStartDate());
		}
		if(Objects.nonNull(detailFilter.getEndDate())) {
			cb.greaterThan(sale.get("saleDate"), detailFilter.getEndDate());
		}
		Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
		return predicate;
	}

}
