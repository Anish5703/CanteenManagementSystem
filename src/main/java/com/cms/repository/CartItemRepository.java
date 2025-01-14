package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.CartItem;
import com.cms.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>{
	
	CartItem findById(int itemId);


}
