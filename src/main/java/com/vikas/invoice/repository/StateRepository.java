package com.vikas.invoice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vikas.invoice.entity.State;

@Repository
public interface StateRepository extends CrudRepository<State, Integer>{

}
