package com.cs.service;

import com.cs.dto.ExecutionOrderDTO;
import com.cs.dto.NewExecutionOrderDTO;

public interface ExecutionOrderService {

    public NewExecutionOrderDTO save(ExecutionOrderDTO dto);
    
}
