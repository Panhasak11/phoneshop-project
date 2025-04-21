package com.nha.java.coding.phone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nha.java.coding.phone.entity.SaleDetail;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{

	List<SaleDetail> findBySaleId(Long saleId);
}
