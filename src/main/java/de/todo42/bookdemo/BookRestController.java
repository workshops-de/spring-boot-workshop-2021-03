package de.todo42.bookdemo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.todo42.bookdemo.public_.tables.pojos.Books;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = BookRestController.REQUEST_URL)
@RequiredArgsConstructor
@Slf4j
public class BookRestController {

    public static final String REQUEST_URL = "/book";

    private final BookService bookService;

    
    
    
    @GetMapping
    public List<Books> getAllBooks() throws Exception {
        List<Books> books = bookService.loadAllBooks();
        log.debug("# books {}", books.size());        
        return books;
    }
    
    @GetMapping(value = "/{isbn}")
    public Book getSingleBooks(@PathVariable(required = true, name = "isbn") String isbn) throws Exception {
        return bookService.loadSingleBooks(isbn);
    }
    
    @PutMapping
    public Book createBook(@RequestBody(required = true) Book newBook) {
        return bookService.create(newBook);
    }
    
    // PUT => create
    
    // POST => update
    
    // DELETE
}
