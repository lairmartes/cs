package com.cs.repository;

import com.cs.entity.BookEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
	
	
}