package com.nha.java.coding.phone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nha.java.coding.phone.dto.ColorDTO;
import com.nha.java.coding.phone.entity.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper {
	
	@Mapping(target = "id",source = "colorId")
	Color toColor(ColorDTO colorDTO);
	
	@Mapping(target = "colorId", source = "id")
	ColorDTO toColorDTO(Color color);
}
