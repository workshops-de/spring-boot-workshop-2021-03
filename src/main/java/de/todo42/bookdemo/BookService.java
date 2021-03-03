package de.todo42.bookdemo;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    

    public List<Book> loadAllBooks() throws Exception {
        return bookRepository.findAllBooks();
    }


    public Book loadSingleBooks(String isbn) throws Exception {
        return bookRepository.findBookByIsbn(isbn);
    }


    public Book create(Book newBook) {
        return bookRepository.create(newBook);
        
    }
    
    
}
