package com.vikas.invoice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.InvoiceSeries;

@Repository
public interface InvoiceSeriesRepository extends CrudRepository<InvoiceSeries, Integer> {

	@Query("SELECT gin FROM InvoiceSeries gin WHERE gin.sequence = :sequence")
	InvoiceSeries findBySequence(String sequence);
}
