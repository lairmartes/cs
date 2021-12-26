package com.cs.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@Table(name="BOOK")
public class BookEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private BookStatus status;

    @OneToMany(mappedBy="book", fetch = FetchType.EAGER)
    private List<OrderEntity> orders;

    @OneToOne(mappedBy = "book" , fetch = FetchType.EAGER)
    private ExecutionOrderEntity executionOrder;

    @OneToMany(mappedBy = "book" , fetch = FetchType.LAZY)
    private List<OrderExecutionResultEntity> executionResult;

    public BookEntity() {
        super();
    }

}