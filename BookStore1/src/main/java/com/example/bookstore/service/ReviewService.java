package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.Review;
import com.example.bookstore.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	  public void addReview(String reviewText, Long bookId) {
	        Review review = new Review();
	        review.setReview(reviewText);
	        review.setBookid(bookId);
	        reviewRepository.save(review);
	    }
	  
	 public List<Review> findReviewsByBookid(Long bookId) {
	        return reviewRepository.findReviewsByBookid(bookId);
	    }
	 
//	  public void addReview(String reviewText, Long bookId) {
//	        Review review = new Review();
//	        review.setReview(reviewText);
//	        review.setBookId(bookId);
//	        reviewRepository.save(review);
//	    }
	 
	 
}


