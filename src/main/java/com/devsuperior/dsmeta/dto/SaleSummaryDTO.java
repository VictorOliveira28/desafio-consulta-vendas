package com.devsuperior.dsmeta.dto;

public class SaleSummaryDTO {
	
	String sellerName;
	Double total;
	
	public SaleSummaryDTO() {		
	}	
	
	public SaleSummaryDTO(String sellerName, Double total) {
		
		this.sellerName = sellerName;
		this.total = total;
	}


	public SaleSummaryDTO(SaleSummaryDTO x) {
		
		sellerName = x.getSellerName();
		total = x.getTotal();
	}		

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}	

}