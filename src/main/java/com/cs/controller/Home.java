package com.cs.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cs.dto.BookDTO;
import com.cs.dto.BookNameDTO;
import com.cs.dto.ExecutionOrderDTO;
import com.cs.dto.NewExecutionOrderDTO;
import com.cs.dto.NewOrderDTO;
import com.cs.dto.OpenBookDTO;
import com.cs.dto.OrderDTO;
import com.cs.service.BookService;
import com.cs.service.ExecutionOrderService;
import com.cs.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    
	// you can replace @Autowire by declaring member atributes as private final
	@Autowire
	private BookService bookService;

	@Autowired
	private OrderService orderService;

	@Autowired 
	private ExecutionOrderService executionOrderService;

	@GetMapping
	public String home() {
		return "<h2>Rest doc ->  <a href='http://localhost:8080/swagger-ui/index.html' target='_blank'>Swagger ui for code challenge</a></h2>";
	}

	// in composed names, it's best call composed name separated with a dash (/open-book)
	@PostMapping(path = "/openBook", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<OpenBookDTO> openBook(	@RequestBody(required = true)
																@NotNull(message = "Book name cannot be null.") 
																@NotEmpty(message = "Book name cannot be empty.") 
																@Valid BookNameDTO book) {
		OpenBookDTO b = this.bookService.create(book);
		ResponseEntity<OpenBookDTO> response =  new ResponseEntity<OpenBookDTO>(b, HttpStatus.OK);
		return response;
	}

	// split entity and service in the path (/order/create)
	@PostMapping(path = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<OrderDTO> createOrder(  @RequestBody(required = true) 
																@NotNull(message = "Order cannot be null.") 
																@NotEmpty(message = "Order cannot be empty.") 
																@Valid NewOrderDTO dto) {
		OrderDTO o = this.orderService.save(dto);
		ResponseEntity<OrderDTO> response =  new ResponseEntity<OrderDTO>(o, HttpStatus.OK);
		return response;
	}

	// same here in path name: (/order/execute) <- note the verb, not the noun
	@PostMapping(path = "/executionOrder", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<NewExecutionOrderDTO> executionOrder(  @RequestBody(required = true) 
																	@NotNull(message = "Order cannot be null.") 
																	@NotEmpty(message = "Order cannot be empty.") 
																	@Valid ExecutionOrderDTO dto) {
		NewExecutionOrderDTO o = this.executionOrderService.save(dto);
		ResponseEntity<NewExecutionOrderDTO> response =  new ResponseEntity<NewExecutionOrderDTO>(o, HttpStatus.OK);
		return response;
	}
	// same here in path name: (/order/execution/book/{id}) <- now "execution" must be a noun.  I just didn't undestand if "book" is another entity.
	@GetMapping(path = "/executionOrder/bookId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<BookDTO> getExecutionResult(@PathVariable("id") @NotNull @NotBlank Long bookId) {
		BookDTO dto = this.bookService.getBookById(bookId);
		ResponseEntity<BookDTO> response =  new ResponseEntity<BookDTO>(dto, HttpStatus.OK);
		return response;	
	}

}
