package com.nha.java.coding.phone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.nha.java.coding.phone.dto.BrandDTO;
import com.nha.java.coding.phone.entity.Brand;


@Mapper(componentModel = "spring")
public interface BrandMapper {
 
	BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
	
//	@Mapping(target = "brandId", source = "brandId")
	Brand toBrand(BrandDTO dto);
	
	BrandDTO toBrandDTO(Brand entity);
	
}
