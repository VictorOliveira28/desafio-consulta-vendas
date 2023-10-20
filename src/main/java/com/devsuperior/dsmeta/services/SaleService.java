package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> searchSummary(String minDate, String maxDate) {
		
		try {
			LocalDate startDate = null;
			LocalDate endDate = null;

			if (minDate != null) {
				startDate = LocalDate.parse(minDate);
				minDate = startDate.toString();
			} else {
				startDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
				minDate = startDate.toString();
			}

			if (maxDate != null) {
				endDate = LocalDate.parse(maxDate);
				maxDate = endDate.toString();
			} else {
				endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
				maxDate = endDate.toString();

			}
			List<SaleSummaryDTO> list = repository.searchSummary(LocalDate.parse(minDate), LocalDate.parse(maxDate));
			List<SaleSummaryDTO> dto = list.stream().map(x -> new SaleSummaryDTO(x))
					.collect(Collectors.toList());

			return dto;

		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
		
	}

	public Page<SaleMinDTO> searchSales(String minDate, String maxDate, String name, Pageable pageable) {
		
		try {
			LocalDate startDate = null;
			LocalDate endDate = null;

			if (minDate != null) {
				startDate = LocalDate.parse(minDate);
				minDate = startDate.toString();
			} else {
				startDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L);
				minDate = startDate.toString();
			}

			if (maxDate != null) {
				endDate = LocalDate.parse(maxDate);
				maxDate = endDate.toString();
			} else {
				endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
				maxDate = endDate.toString();

			}
		Page<SaleMinDTO> result = repository.searchSales(LocalDate.parse(minDate),
				LocalDate.parse(maxDate), name, pageable);		
		  
		return result.map(x -> new SaleMinDTO(x));
	}catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
}