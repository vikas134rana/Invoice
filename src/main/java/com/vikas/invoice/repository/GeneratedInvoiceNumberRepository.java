package com.vikas.invoice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.GeneratedInvoiceNumber;

@Repository
public interface GeneratedInvoiceNumberRepository extends CrudRepository<GeneratedInvoiceNumber, Integer> {

	@Query("SELECT gin FROM GeneratedInvoiceNumber gin WHERE gin.sequence = :sequence")
	GeneratedInvoiceNumber findBySequence(String sequence);
}
