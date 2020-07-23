package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.ItemPrice;

@Repository
public interface ItemPriceRepository extends CrudRepository<ItemPrice, Integer> {

}
