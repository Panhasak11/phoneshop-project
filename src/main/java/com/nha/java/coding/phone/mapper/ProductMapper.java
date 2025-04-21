package com.nha.java.coding.phone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nha.java.coding.phone.dto.ProductDTO;
import com.nha.java.coding.phone.dto.ProductImportDTO;
import com.nha.java.coding.phone.entity.Product;
import com.nha.java.coding.phone.entity.ProductImportHistory;
import com.nha.java.coding.phone.service.ColorService;
import com.nha.java.coding.phone.service.ModelService;

//provide mapstruct for convert id (modelid, colorId) into an entity (model =, color ) 
@Mapper(componentModel = "spring", uses = {ModelService.class,ColorService.class})
public interface ProductMapper {

	@Mapping(target = "color", source = "colorId")
	@Mapping(target = "model", source = "modelId")
	Product toProduct(ProductDTO productDTO);
	
	@Mapping(target = "colorId", ignore = true)
	@Mapping(target = "modelId", ignore = true)
	ProductDTO toProductDTO(Product product);
	
	@Mapping(target = "product", source = "product")
	@Mapping(target = "id", ignore = true)
	ProductImportHistory toProductImportHistory(ProductImportDTO importDTO, Product product);
	
}
