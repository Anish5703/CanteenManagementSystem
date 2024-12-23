package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	User findByUsername(String username);
    User findByPhone(String Phone);
    User findByCookieId(String coookieId);

}
