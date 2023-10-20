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
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

import projections.SaleMinProjection;

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
			if (minDate == null) {
				LocalDate result = LocalDate.parse(maxDate).minusYears(1L);
				minDate = result.toString();

			}
			if (maxDate == null) {
				LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
				maxDate = today.toString();
			}
			List<SaleSummaryDTO> list = repository.searchSummary(LocalDate.parse(minDate),
					LocalDate.parse(maxDate));
			List<SaleSummaryDTO> dto = list.stream().map(x -> new SaleSummaryDTO(x)).collect(Collectors.toList());
			
			return dto;

		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

}