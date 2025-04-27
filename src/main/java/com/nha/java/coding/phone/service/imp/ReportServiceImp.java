package com.nha.java.coding.phone.service.imp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.dto.ExpenseReportDTO;
import com.nha.java.coding.phone.dto.ProductReportDTO;
import com.nha.java.coding.phone.entity.Product;
import com.nha.java.coding.phone.entity.ProductImportHistory;
import com.nha.java.coding.phone.entity.SaleDetail;
import com.nha.java.coding.phone.repository.ProductImportHistoryRepository;
import com.nha.java.coding.phone.repository.ProductRepository;
import com.nha.java.coding.phone.repository.SaleDetailRepository;
import com.nha.java.coding.phone.service.ReportServie;
import com.nha.java.coding.phone.specification.ProductImportHistoryFilter;
import com.nha.java.coding.phone.specification.ProductImportHistorySpec;
import com.nha.java.coding.phone.specification.SaleDetailFilter;
import com.nha.java.coding.phone.specification.SaleDetailSpec;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements ReportServie{

	
	private final SaleDetailRepository saleDetailRepository;
	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository importHistoryRepository;
	
	@Override
	public List<ProductReportDTO> getProductReport(LocalDate startDate, LocalDate endDate) {
		
		List<ProductReportDTO> reportList = new ArrayList<>();
		
		SaleDetailFilter saleDetailFilter = new SaleDetailFilter();
		saleDetailFilter.setStartDate(startDate);
		saleDetailFilter.setEndDate(endDate);
		
		SaleDetailSpec spec = new SaleDetailSpec(saleDetailFilter);
		List<SaleDetail> saleDetails = saleDetailRepository.findAll(spec);
		
		//map to List of productIds
		List<Long> productIds = saleDetails.stream()
			.map(sd -> sd.getProduct().getId())
			.toList();
		
		//convert productIds to productMap that have key and Object 
		//key = long, Object = Product
		Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		//group saleDetail to list by product
		Map<Product, List<SaleDetail>> saleDetailMap = saleDetails.stream()
			.collect(Collectors.groupingBy(SaleDetail::getProduct));
		
		for(var entry : saleDetailMap.entrySet()) {
			Product product = productMap.get(entry.getKey().getId());
			List<SaleDetail> sdList = entry.getValue();
			
			//total unit
			Integer units = sdList.stream()
				.map(SaleDetail::getUnit)
				.reduce(0, (a,b) -> a+b);
			
			//total amount
			double totalAmount = sdList.stream()
				.mapToDouble(sd -> sd.getUnit() * sd.getAmount().doubleValue())
				.sum();
				
			
			ProductReportDTO reportDTO = new ProductReportDTO();
			reportDTO.setProductId(product.getId());
			reportDTO.setProductName(product.getName());
			reportDTO.setUnit(units);
			reportDTO.setTotalAmout(BigDecimal.valueOf(totalAmount));
			reportList.add(reportDTO);
		}
		
		return reportList;
		
	}

	@Override
	public List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate) {
		
		ProductImportHistoryFilter historyFilter = new ProductImportHistoryFilter();
		historyFilter.setStartDate(startDate);
		historyFilter.setEndDate(endDate);
		
		ProductImportHistorySpec importHistorySpec = new ProductImportHistorySpec(historyFilter);
		List<ProductImportHistory> importHistories = importHistoryRepository.findAll(importHistorySpec);
		
		Set<Long> productIds = importHistories.stream()
			.map(his -> his.getProduct().getId())
			.collect(Collectors.toSet());
		
		Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		Map<Product, List<ProductImportHistory>> historyMap = importHistories.stream()
			.collect(Collectors.groupingBy(ps -> ps.getProduct()));
		
//		var expenseReportDTO = new ArrayList<ExpenseReportDTO>();
		List<ExpenseReportDTO> expenseReportDTOs = new ArrayList<>();
		
		for(var entry:historyMap.entrySet()) {
			Product product = productMap.get(entry.getKey().getId());
			List<ProductImportHistory> productImportHistories = entry.getValue();
			
			int totalUnit = productImportHistories.stream()
				.mapToInt(ps -> ps.getImportUnit())
				.sum();
			
			double totalAmount = productImportHistories.stream()
				.mapToDouble(ps -> ps.getImportUnit() * ps.getUnitPrice().doubleValue())
				.sum();
			
			var expenseReportDTO = new ExpenseReportDTO();
			expenseReportDTO.setProductId(product.getId());
			expenseReportDTO.setProductName(product.getName());
			expenseReportDTO.setTotalUnit(totalUnit);
			expenseReportDTO.setTotalAmount(BigDecimal.valueOf(totalAmount));
			expenseReportDTOs.add(expenseReportDTO);
		}
		
		Collections.sort(expenseReportDTOs, (a,b)-> (int)(a.getProductId() - b.getProductId()));
		
		return expenseReportDTOs;
	}

}
