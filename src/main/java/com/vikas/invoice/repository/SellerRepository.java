package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Seller;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Integer> {

}
