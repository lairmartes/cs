package com.cs.service;

import com.cs.dto.NewOrderDTO;
import com.cs.dto.OpenBookDTO;
import com.cs.dto.OrderDTO;
import com.cs.entity.BookEntity;
import com.cs.entity.OrderEntity;
import com.cs.repository.BookRepository;
import com.cs.repository.OrderRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRep;

    @Transactional
    @Override
    public OrderDTO save(NewOrderDTO dto) {
        BookEntity book = this.bookRep.findById(dto.getBookId()).orElse(null);

        OrderEntity entity = OrderEntity.builder()
                                            .quantity(dto.getQuantity())
                                            .price(dto.getPrice())
                                            .type(dto.getType())
                                            .book(book)
                                            .build();
        this.orderRepository.save(entity);
        Hibernate.initialize(entity.getBook());
        OrderDTO orderDTOs = OrderDTO.builder()
                            .id(entity.getId())
                            .quantity(entity.getQuantity())
                            .price(entity.getPrice())
                            .book(OpenBookDTO.bookNameBuilder()
                                                .bookName(entity.getBook()
                                                            .getName())
                                                .id(entity.getBook()
                                                            .getId())
                                                .status(entity.getBook()
                                                            .getStatus())
                                    .build())       
                            .build();

        return orderDTOs;
    }
    
}