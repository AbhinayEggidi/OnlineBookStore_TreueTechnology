package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.repository.CartRepository;

@Service
public class CartService {
	
	  @Autowired
	    private CartRepository cartRepository;

	    public Cart addToCart(Cart cartItem) {
	        return cartRepository.save(cartItem);
	    }
	    public List<Cart> getCartByUserId(Long userId) {
	        return cartRepository.findCartsByUserid(userId);
	    }
	    
	    public void removeCartItem(Long cartId) {
	         
	        cartRepository.deleteById(cartId);
	    }
	     


}
