package com.nha.java.coding.phone.projection;

import java.math.BigDecimal;

public interface ProductSold {

	//productId, productName, unit, totalAmount
	Long getProductid();
	String getProductName();
	Integer getUnit();
	BigDecimal getTotalAmount();
	
}
