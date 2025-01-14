package com.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.Cart;
import com.cms.entity.User;

public interface CartRepository extends JpaRepository<Cart,Integer>{
	
    Optional<Cart> findByUser(User user);
    Cart findCartByUser(User user);
    

}
