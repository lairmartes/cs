package com.cs.dto;

import com.cs.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
// it looks like this is the only class that is extending OpenBookDTO. Even that class is extending another class with one field... it this hierarchy necessary?
public class NewExecutionOrderDTO extends OpenBookDTO{

	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer quantity;

    private Long price;

    @Builder(builderMethodName = "bookBuilder")
    public NewExecutionOrderDTO(Long id, Integer quantity, Long price, Long bookId, BookStatus status, String bookName) {
        super(bookId, status, bookName);
        this.id         = id;
        this.quantity   = quantity;
        this.price      = price;
    }

}
