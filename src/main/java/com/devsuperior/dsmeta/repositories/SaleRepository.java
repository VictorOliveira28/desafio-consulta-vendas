package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "			
			+ "FROM Sale obj " 			
			+ "WHERE obj.date >= :minDate " 
			+ "AND obj.date <= :maxDate " 
			+ "GROUP BY obj.seller.name")
	List<SaleSummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);

	
	
	@Query(nativeQuery = true, value = "SELECT "
			+ "s.name AS seller_name, "			
			+ "SUM(amount) AS total_amount "
			+ "FROM "
			+ "tb_sales sa "
			+ "INNER JOIN "
			+ "tb_seller s ON sa.seller_id = s.id "
			+ "WHERE "
			+ "sa.date >= :minDate AND "
			+ "sa.date <= :maxDate AND "
			+ "s.name = :name "
			+ "GROUP BY "
			+ "seller_name")
	Page<SaleMinDTO> searchSales(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	

}
