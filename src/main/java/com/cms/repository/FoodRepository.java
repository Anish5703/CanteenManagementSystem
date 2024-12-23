package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.Food;

public interface FoodRepository extends JpaRepository<Food,Integer>{

	
	Food findByName(String foodName);
	Food findById(int id);
}
