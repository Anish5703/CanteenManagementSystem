package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{

	Order findOrderById(int id);
	
    List<Order> findAllByOrderByCreatedAtDesc();

}
