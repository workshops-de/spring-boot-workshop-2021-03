package de.todo42.bookdemo;

import static de.todo42.bookdemo.public_.Tables.BOOKS;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    
    private final DSLContext jooq;
    
    
    public Result loadAllBooks() throws Exception {
        // @formatter:off
        Condition whereClause = DSL.noCondition();
        whereClause = whereClause.and(BOOKS.ISBN.startsWith("2"));
        
        return jooq.select(BOOKS.TITLE)
                .from(BOOKS)
                .where(whereClause)
                .fetch();
 
	    // @formatter:on

    }


    public Book loadSingleBooks(String isbn) throws Exception {
        return bookRepository.findBookByIsbn(isbn);
    }


    public Book create(Book newBook) {
        return bookRepository.create(newBook);
        
    }
    
    
}
