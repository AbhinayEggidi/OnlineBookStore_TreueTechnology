package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.bookstore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	 @Query("SELECT c FROM Cart c WHERE c.userid = :userId")
	    List<Cart> findCartsByUserid(@Param("userid") Long userId);
}
