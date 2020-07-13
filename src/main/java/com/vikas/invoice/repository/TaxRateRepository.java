package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.TaxRate;

@Repository
public interface TaxRateRepository extends CrudRepository<TaxRate, Integer>{

}
