package com.cs.service;

import com.cs.dto.BookDTO;
import com.cs.dto.BookNameDTO;
import com.cs.dto.OpenBookDTO;

public interface BookService {

    public OpenBookDTO create(BookNameDTO book);

	public BookDTO getBookById(Long bookId);
    
}
