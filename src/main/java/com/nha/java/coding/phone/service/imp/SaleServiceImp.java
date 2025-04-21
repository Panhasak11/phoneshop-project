package com.nha.java.coding.phone.service.imp;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.dto.ProductSoldDTO;
import com.nha.java.coding.phone.dto.SaleDTO;
import com.nha.java.coding.phone.entity.Product;
import com.nha.java.coding.phone.entity.Sale;
import com.nha.java.coding.phone.entity.SaleDetail;
import com.nha.java.coding.phone.exception.ApiException;
import com.nha.java.coding.phone.exception.ResourceNotFoundException;
import com.nha.java.coding.phone.repository.ProductRepository;
import com.nha.java.coding.phone.repository.SaleDetailRepository;
import com.nha.java.coding.phone.repository.SaleRepository;
import com.nha.java.coding.phone.service.ProductService;
import com.nha.java.coding.phone.service.SaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImp implements SaleService{

	private final ProductService productService;
	private final ProductRepository productRepository;
	private final SaleRepository saleRepository;
	private final SaleDetailRepository saleDetailRepository;
	
	
	@Override
	public void sell(SaleDTO saleDTO) {
		//get product from saleDto by use stream
		List<Long> productId = saleDTO.getProducts().stream()
			.map(ProductSoldDTO::getProductId)
			.collect(Collectors.toList());
		
		//validate product
		productId.forEach(productService::findById);
		
		List<Product> products = productRepository.findAllById(productId);
		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		//validate stock
		saleDTO.getProducts()
			.forEach(ps->{
				Product product = productMap.get(ps.getProductId());
				if(product.getAvailableUnit()< ps.getNumberOfUnit()) {
					throw new ApiException(HttpStatus.BAD_REQUEST, "Produc [%s] is not enough in stock".formatted(product.getName()));
				}
			});
		
		//save sale
		Sale sale = new Sale();
		sale.setSaleDate(saleDTO.getSaleDate());
		saleRepository.save(sale);
		
		//save saleDetail
		saleDTO.getProducts().forEach(ps -> {
			Product product = productMap.get(ps.getProductId());
			SaleDetail saleDetail = new SaleDetail();
			saleDetail.setAmount(product.getSalePrice());
			saleDetail.setProduct(product);
			saleDetail.setSale(sale);
			saleDetail.setUnit(ps.getNumberOfUnit());
			saleDetailRepository.save(saleDetail);
			
			
			//cut stock
			Integer availanleUnit = product.getAvailableUnit() - ps.getNumberOfUnit();
			product.setAvailableUnit(availanleUnit);
			productRepository.save(product);
		});
	}


	@Override
	public Sale getById(Long saleId) {
		return saleRepository.findById(saleId)
			.orElseThrow(() -> new ResourceNotFoundException("Sale", saleId));
	}


	@Override
	public void cancelSale(Long saleId) {
		Sale sale = getById(saleId);
		sale.setActive(false);
		saleRepository.save(sale);
		
		
//		update product stock
		List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(saleId);
		List<Long> productIds = saleDetails.stream()
			.map(sd -> sd.getProduct().getId())
			.toList();
		
		List<Product> products = productRepository.findAllById(productIds);
		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		saleDetails.forEach(sd ->{
			Product product = productMap.get(sd.getProduct().getId());
			product.setAvailableUnit(product.getAvailableUnit() + sd.getUnit());
			productRepository.save(product);
		});
	}

}
