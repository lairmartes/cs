package com.cs.repository;

import com.cs.entity.OrderExecutionResultEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderExecutionResultRepository extends JpaRepository<OrderExecutionResultEntity, Long> {
	
	
}