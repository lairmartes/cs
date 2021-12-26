package com.cs.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BookNameDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Book name is mandatory")
	@NotNull(message = "Book name is mandatory")
    private String name;

    public BookNameDTO() {
		super();
		this.name = "";

	}
}