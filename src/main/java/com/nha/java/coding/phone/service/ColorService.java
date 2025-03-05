package com.nha.java.coding.phone.service;

import java.util.List;

import com.nha.java.coding.phone.entity.Color;

public interface ColorService {

	Color create(Color color);
	Color getById(Long id);
	List<Color> getAllColor();
}
