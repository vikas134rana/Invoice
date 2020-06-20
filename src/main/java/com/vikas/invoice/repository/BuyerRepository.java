package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Buyer;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer, Integer> {

}
