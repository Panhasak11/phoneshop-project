package com.nha.java.coding.phone.service;

import com.nha.java.coding.phone.dto.SaleDTO;
import com.nha.java.coding.phone.entity.Sale;

public interface SaleService {

	void sell(SaleDTO saleDTO);
	Sale getById(Long saleId);
	void cancelSale(Long saleId);
}
