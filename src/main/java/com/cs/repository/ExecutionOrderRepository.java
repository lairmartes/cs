package com.cs.repository;

import com.cs.entity.ExecutionOrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionOrderRepository extends JpaRepository<ExecutionOrderEntity, Long> {
	
	
}