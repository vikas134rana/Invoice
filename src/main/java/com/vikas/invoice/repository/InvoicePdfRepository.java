package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.InvoicePdf;

@Repository
public interface InvoicePdfRepository extends CrudRepository<InvoicePdf, Integer>{

}
