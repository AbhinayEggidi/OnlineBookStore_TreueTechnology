package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
	@Query("SELECT r FROM Review r WHERE r.book.id = :bookId")
	List<Review> findReviewsByBookid(@Param("bookid") Long bookId);

}
