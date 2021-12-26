package com.cs.dto;

import java.io.Serializable;
import java.util.List;

import com.cs.enums.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
// Serializable is not required for Json serialization.
public class BookDTO implements Serializable{

	// not required
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;
    
    private BookStatus status;

    private List<OrderDTO> orders;

    private List<OrderExecutionResultDTO> executionResult
	    
    private NewExecutionOrderDTO executionOrder;
}
