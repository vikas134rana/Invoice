package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer>{

}
