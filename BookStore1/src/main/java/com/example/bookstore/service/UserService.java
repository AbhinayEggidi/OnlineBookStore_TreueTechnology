package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.User;
import com.example.bookstore.repository.UserRepository;

@Service
public class UserService 
{
	 @Autowired
	    private UserRepository userRepository;

	    public User register(User user) {
	        // Implement registration logic, e.g., validation, encryption, etc.
	        return userRepository.save(user);
	    }

	    public User login(String username, String password) {
	        // Implement login logic, e.g., validation, authentication, etc.
	        return userRepository.findByUsername(username);
	    }
}
