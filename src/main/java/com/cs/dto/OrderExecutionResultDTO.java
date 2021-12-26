package com.cs.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.cs.enums.ExecutionOrderResultType;
import com.cs.validation.EnumOrderExecutionTypePattern;
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
public class OrderExecutionResultDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer quantity;

    private Long price;

    @NotNull(message = "Order type is mandatory")
    @EnumOrderExecutionTypePattern(anyOf = {ExecutionOrderResultType.EXECUTED, ExecutionOrderResultType.INVALID})
    private ExecutionOrderResultType status;

    private BookDTO book;

}
