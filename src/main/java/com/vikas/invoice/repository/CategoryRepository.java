package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{

}
