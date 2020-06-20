package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.InvoiceItem;

@Repository
public interface InvoiceItemRepository extends CrudRepository<InvoiceItem, Integer>{

}
