package com.cs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.dto.BookDTO;
import com.cs.dto.BookNameDTO;
import com.cs.dto.NewExecutionOrderDTO;
import com.cs.dto.OpenBookDTO;
import com.cs.dto.OrderDTO;
import com.cs.dto.OrderExecutionResultDTO;
import com.cs.entity.BookEntity;
import com.cs.enums.BookStatus;
import com.cs.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository rep;

    @Transactional
    @Override
    public OpenBookDTO create(BookNameDTO book) {
        BookEntity entity = BookEntity.builder()
                                        .name(book.getName())
                                        .status(BookStatus.OPENED)
                                        .build();
        this.rep.save(entity);
        OpenBookDTO bookDTO = OpenBookDTO.bookNameBuilder()
                                            .bookName(entity.getName())
                                            .id(entity.getId())
                                            .status(entity.getStatus())
                                            .build();
        return bookDTO;
    }

	@Override
	public BookDTO getBookById(Long bookId) {
		BookEntity e = this.rep.findById(bookId).orElse(null);
		Hibernate.initialize(e.getExecutionResult());
		List<OrderExecutionResultDTO> results = e.getExecutionResult().stream().map(r->	OrderExecutionResultDTO
					.builder()
					.id(r.getId())
					.price(r.getPrice())
					.quantity(r.getQuantity())
					.status(r.getStatus())
					.book(null)
					.build()).collect(Collectors.toList());
		
		NewExecutionOrderDTO order = NewExecutionOrderDTO.bookBuilder()
													.id(e.getExecutionOrder().getId())
													.price(e.getExecutionOrder().getPrice())
													.quantity(e.getExecutionOrder().getQuantity())
													.bookId(null)
													.build();
		
		List<OrderDTO> orders = e.getOrders().stream().map(o->
													OrderDTO.builder()
															.id(o.getId())
															.quantity(o.getQuantity())
															.price(o.getPrice())
															.type(o.getType())
															.book(null)
															.build()
													).collect(Collectors.toList());
		BookDTO dto = BookDTO.builder()
								.id(e.getId())
								.name(e.getName())
								.status(e.getStatus())
								.executionResult(results)
								.executionOrder(order)
								.orders(orders)
								.build();
		return dto;
	}
	
	
	/**
	private Set<OrderDTO> orders;

	 */
    
}