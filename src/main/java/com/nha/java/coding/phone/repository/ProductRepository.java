package com.nha.java.coding.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nha.java.coding.phone.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	

}
