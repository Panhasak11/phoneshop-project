package com.nha.java.coding.phone.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nha.java.coding.phone.dto.ColorDTO;
import com.nha.java.coding.phone.entity.Color;
import com.nha.java.coding.phone.mapper.ColorMapper;
import com.nha.java.coding.phone.service.ColorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("colors")
public class ColorController {

	
	private final ColorMapper colorMapper;
	private final ColorService colorService;
	
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ColorDTO colorDTO){
		Color color = colorMapper.toColor(colorDTO);
		color = colorService.create(color);
		return ResponseEntity.ok(color);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long colorId){
		Color color = colorService.getById(colorId);
		return ResponseEntity.ok(colorMapper.toColorDTO(color));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<Color> colors = colorService.getAllColor();
		List<ColorDTO> list = colors.stream()
			.map(colorMapper::toColorDTO)
			.toList();
			
		return ResponseEntity.ok(list);
	}
	
	
}
