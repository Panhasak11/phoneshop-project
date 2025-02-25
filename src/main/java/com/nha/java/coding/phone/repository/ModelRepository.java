package com.nha.java.coding.phone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nha.java.coding.phone.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Long>{
	List<Model> findByBrandId(Long brandId);
}
