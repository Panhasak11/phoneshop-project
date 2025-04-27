package com.nha.java.coding.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nha.java.coding.phone.entity.ProductImportHistory;

public interface ProductImportHistoryRepository extends JpaRepository<ProductImportHistory, Long>, 
					JpaSpecificationExecutor<ProductImportHistory>{

}
