package de.todo42.bookdemo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class BookRepository {

    private final ObjectMapper mapper;
    
    private List<Book> books;
    
    
    @Autowired
    public BookRepository(ObjectMapper mapper) throws Exception {
        this.mapper = mapper;
        books = new ArrayList<>();
        books.addAll(Arrays.asList(mapper.readValue(new File("books.json"), Book[].class)));        
    }
    
    public List<Book> findAllBooks() throws Exception {
        return books;
    }


    public Book findBookByIsbn(final String isbn) throws Exception {
        return books.stream().filter(b -> b.getIsbn().equals(isbn))
                .collect(Collectors.toList()).get(0);
    }

    public Book create(Book newBook) {
        books.add(newBook);
        return newBook;
    }

}
