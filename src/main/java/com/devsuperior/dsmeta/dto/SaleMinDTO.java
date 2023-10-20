package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;

import projections.SaleMinProjection;

public class SaleMinDTO {

	private Long id;
	private Double amount;
	private LocalDate date;
	private String name;
	
	public SaleMinDTO(Long id, Double amount, LocalDate date) {
		this.id = id;
		this.amount = amount;
		this.date = date;
	}
	
	public SaleMinDTO(Sale entity) {
		id = entity.getId();
		amount = entity.getAmount();
		date = entity.getDate();		
	}
	
	public SaleMinDTO(SaleMinProjection projection) {
		id = projection.getId();
		amount = projection.getAmount();
		date = projection.getDate();
		name = projection.getName();
	}
	
	public SaleMinDTO(SaleSummaryDTO x) {
		name = x.getSellerName();
		amount = x.total;
	}

	public SaleMinDTO(SaleMinDTO x) {
		id = x.getId();
		date = x.getDate();
		amount = x.getAmount();
		name = x.getName();
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getName() {
		return name;
	}
		
}
