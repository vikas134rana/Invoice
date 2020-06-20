package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer>{

}
