package com.nha.java.coding.phone.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.entity.Color;
import com.nha.java.coding.phone.exception.ResourceNotFoundException;
import com.nha.java.coding.phone.repository.ColorRepository;
import com.nha.java.coding.phone.service.ColorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImp implements ColorService{

	private final ColorRepository colorRepository;
	
	@Override
	public Color create(Color color) {
		return colorRepository.save(color);
	}

	@Override
	public List<Color> getAllColor() {
		return colorRepository.findAll();
	}

	@Override
	public Color getById(Long id) {
		return colorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Color", id));
	}

}
