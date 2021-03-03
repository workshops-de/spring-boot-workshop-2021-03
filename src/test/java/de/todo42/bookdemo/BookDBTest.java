package de.todo42.bookdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class BookDBTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    BookJpaRepository bookRepository;

    @Autowired
    PublisherJpaRepository publisherRepository;
    
    @Autowired
    EntityManager em;
    
    
    @Test
    void testInsert() throws Exception {
        String sql = "INSERT INTO books (isbn, author, description, title) VALUES ('12345', 'Dominik', 'Test', 'Der Titel')";
        int count = jdbcTemplate.update(sql);
        //assertEquals(1,  count);
    }
    
    @Test
    //@Transactional
    void testJpaCrud() throws Exception {
        Book book = createBook(1).get(0);
        
        Book result = bookRepository.save(book);
                
        //em.flush();
        
        Book result2 = bookRepository.findById(book.getId()).get();
        assertNotNull(result2);
    }

    @Test
    void testRelation() {
        List<Book> books = createBook(10);
        
        Publisher publisher = new Publisher();
        publisher.setName("Test");
        for (Book book : books) {
            publisher.addBook(book);
        }
        
        publisherRepository.save(publisher);
        
       assertEquals(1L, publisherRepository.count());
       assertEquals(10L, bookRepository.count());
    }

    
    private List<Book> createBook(int size) {
        List<Book> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Book book = Book.builder()
                    .isbn( i + "-1234")
                    .title("title " + i)
                    .author("author")
                    .description("descr")
                    .build();
            result.add(book);
        }
        return result;
    }
    
    
    
    
}
