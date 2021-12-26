package com.cs.service;

import com.cs.dto.NewOrderDTO;
import com.cs.dto.OrderDTO;

public interface OrderService {
    public OrderDTO save(NewOrderDTO dto);
}
