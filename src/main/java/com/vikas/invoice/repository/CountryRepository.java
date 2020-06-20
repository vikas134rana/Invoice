package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer>{
	
}
