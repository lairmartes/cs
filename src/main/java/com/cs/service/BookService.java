package com.cs.service;

import com.cs.dto.BookDTO;
import com.cs.dto.BookNameDTO;
import com.cs.dto.OpenBookDTO;

public interface BookService {

	// I'd recommed you to move references from controller classes in service
    public OpenBookDTO create(BookNameDTO book);

	public BookDTO getBookById(Long bookId);
    
}
