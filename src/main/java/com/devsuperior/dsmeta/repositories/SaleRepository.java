package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

import projections.SaleMinProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj " + "WHERE obj.date >= :minDate " + "AND obj.date <= :maxDate "
			+ "GROUP BY obj.seller.name")
	List<SaleSummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);

	@Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, " + "tb_seller.name "
			+ "FROM tb_sales " + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE tb_sales.date >= :minDate " + "  AND tb_sales.date <= :maxDate "
			+ "  AND UPPER(tb_seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
	Page<SaleMinProjection> searchSales(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

}
