package com.barman.barman.dao;

import org.springframework.data.repository.CrudRepository;

import com.barman.barman.domain.Drink;

public interface IDrinksDao extends CrudRepository<Drink, Long>{

	Drink findByName(String name);
}
