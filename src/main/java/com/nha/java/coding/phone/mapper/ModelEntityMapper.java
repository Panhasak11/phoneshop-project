package com.nha.java.coding.phone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.nha.java.coding.phone.dto.ModelDTO;
import com.nha.java.coding.phone.dto.ModelUpdateDTO;
import com.nha.java.coding.phone.entity.Model;
import com.nha.java.coding.phone.service.BrandService;

@Mapper(componentModel = "spring", uses = BrandService.class)
public interface ModelEntityMapper {
	
	ModelEntityMapper INSTANCE = Mappers.getMapper(ModelEntityMapper.class);
	
	@Mapping(target = "brand",source = "brandId")
	Model toModel(ModelDTO modelDTO);
	
	@Mapping(target = "brandId",source = "brand.id")
	ModelDTO toModelDTO(Model model);
	
	@Mapping(target = "id",source = "id")
	Model toModel(ModelUpdateDTO modelUpdateDTO);
	
	@Mapping(target = "id" , source= "id")
	ModelUpdateDTO modelUpdateDTO(Model model);
}
