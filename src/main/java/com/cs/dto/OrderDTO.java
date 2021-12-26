package com.cs.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.cs.enums.OrderType;
import com.cs.validation.EnumOrderTypePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(Include.NON_NULL)
public class OrderDTO implements Serializable{

	private static final long serialVersionUID = 1L;

    @NotNull(message = "Order id is mandatory")
	private Long id;

    @NotNull(message = "Order quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "Order price is mandatory")
    private Long price;

    @NotNull(message = "Order type is mandatory")
    @EnumOrderTypePattern(anyOf = {OrderType.LIMIT, OrderType.MARKET})
    private OrderType type;

    private OpenBookDTO book;

}
