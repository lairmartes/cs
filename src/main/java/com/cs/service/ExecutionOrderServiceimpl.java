package com.cs.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import com.cs.dto.ExecutionOrderDTO;
import com.cs.dto.NewExecutionOrderDTO;
import com.cs.entity.BookEntity;
import com.cs.entity.ExecutionOrderEntity;
import com.cs.entity.OrderExecutionResultEntity;
import com.cs.enums.BookStatus;
import com.cs.enums.ExecutionOrderResultType;
import com.cs.enums.OrderType;
import com.cs.repository.BookRepository;
import com.cs.repository.ExecutionOrderRepository;
import com.cs.repository.OrderExecutionResultRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionOrderServiceimpl implements ExecutionOrderService{
    
    @Autowired
    private ExecutionOrderRepository executionOrderRepository;

    @Autowired
    private OrderExecutionResultRepository orderExecutionResultRepository;

    @Autowired
    private BookRepository bookRep;


    @Transactional
    @Override
    public NewExecutionOrderDTO save(ExecutionOrderDTO dto) {
        BookEntity book = this.bookRep.findById(dto.getBookId()).orElse(null);
        ExecutionOrderEntity entity = extractExecutionOrder(dto, book);
       
        List<OrderExecutionResultEntity> orderExecList =  extractOrderExecList(dto, book);
        
        distributionOfOrderQuantity(dto, orderExecList);

        this.orderExecutionResultRepository.saveAll(orderExecList);
        this.executionOrderRepository.save(entity);
        NewExecutionOrderDTO newExecutionOrderDTO = NewExecutionOrderDTO.bookBuilder()
        													.id(entity.getId())
                                                            .quantity(entity.getQuantity())
                                                            .price(entity.getPrice())
                                                            .bookId(entity.getBook().getId())
                                                            .bookName(entity.getBook().getName())
                                                            .status(entity.getBook().getStatus())
                                                            .build();

        return newExecutionOrderDTO;
    }

    private void distributionOfOrderQuantity(final ExecutionOrderDTO dto, final List<OrderExecutionResultEntity> orderExecList) {
        Long quantityOfExecution = orderExecList.stream().filter(o->o.getStatus().equals(ExecutionOrderResultType.EXECUTED)).count();
        AtomicInteger counter = new AtomicInteger(dto.getQuantity());
        counter.incrementAndGet();
        Integer initial  = 1;

		List<Integer> i =  IntStream.range(initial, counter.intValue())
                .map(x -> Integer.valueOf(counter.intValue() - x + initial - 1))
                .filter(x-> (counter.intValue() - x + initial - 1) % quantityOfExecution.intValue() == 0 )
                .boxed().collect(Collectors.toList());

        if (!i.isEmpty() && i.get(0) > 1 ) {
            orderExecList.stream().forEach(o->{
                    if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
                        o.setQuantity((i.get(0)+1)/quantityOfExecution.intValue());
                        o.setPrice(dto.getPrice());
                    }
                }); 
            
            int total = dto.getQuantity().intValue() - i.get(0);
            if( total > 0 ){
                for( int x = 1  ; x <= total  ; x++ ){
                    for(OrderExecutionResultEntity o : orderExecList){
                        if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
                            o.setPrice(dto.getPrice());
                            int q = o.getQuantity();
                            q++;
                            o.setQuantity(q);
                            break;
                        }
                    }
                }
            }
        }else{
            for(OrderExecutionResultEntity o : orderExecList){
                if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
                    o.setQuantity(dto.getQuantity());
                    o.setPrice(dto.getPrice());
                    break;
                }
            }
            orderExecList.stream().forEach(o->{
                if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) && o.getQuantity() == -1){
                    o.setQuantity(0);
                    o.setStatus(ExecutionOrderResultType.INVALID);
                    o.setPrice(dto.getPrice());
                }
            });
        }
    }

    private ExecutionOrderEntity extractExecutionOrder(final ExecutionOrderDTO dto, final BookEntity book) {
        book.setStatus(BookStatus.CLOSED);
        ExecutionOrderEntity entity = ExecutionOrderEntity.builder()
                                                            .quantity(dto.getQuantity())
                                                            .price(dto.getPrice())
                                                            .book(book)
                                                            .build();
        return entity;
    }

    private List<OrderExecutionResultEntity> extractOrderExecList(final ExecutionOrderDTO dto, final BookEntity book) {
        return  book.getOrders()
                    .stream()
                    .map(o->{
                                OrderExecutionResultEntity e;
                                if(o.getType().equals(OrderType.LIMIT) && 
                                       ( o.getPrice() == dto.getPrice() ||  dto.getPrice() > o.getPrice() ) ){
                                    e = OrderExecutionResultEntity
                                        .builder()
                                        .quantity(-1)
                                        .price(dto.getPrice())
                                        .status(ExecutionOrderResultType.EXECUTED)
                                        .book(book)
                                        .build();
                                }else if(o.getType().equals(OrderType.MARKET)){
                                    e = OrderExecutionResultEntity
                                        .builder()
                                        .quantity(-1)
                                        .price(dto.getPrice())
                                        .status(ExecutionOrderResultType.EXECUTED)
                                        .book(book)
                                        .build();
                                }else{
                                    e = OrderExecutionResultEntity
                                        .builder()
                                        .quantity(0)
                                        .price(dto.getPrice())
                                        .status(ExecutionOrderResultType.INVALID)
                                        .book(book)
                                        .build();
                                }
                                return e;
                            }).collect(Collectors.toList());
    }
    
}