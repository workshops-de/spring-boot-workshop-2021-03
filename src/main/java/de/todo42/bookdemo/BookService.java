package de.todo42.bookdemo;

import static de.todo42.bookdemo.public_.Tables.BOOKS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    
    private final DSLContext jooq;
    
    
    public List<Book> loadAllBooks() throws Exception {
        //return bookRepository.findAllBooks();
        return jooq.selectFrom(BOOKS).fetchInto(Book.class);
    }


    public Book loadSingleBooks(String isbn) throws Exception {
        return bookRepository.findBookByIsbn(isbn);
    }


    public Book create(Book newBook) {
        return bookRepository.create(newBook);
        
    }
    
    
}
