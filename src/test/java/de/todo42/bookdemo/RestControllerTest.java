package de.todo42.bookdemo;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.todo42.bookdemo.public_.tables.pojos.Books;

@SpringBootTest
class RestControllerTest {

    @Autowired
    private BookRestController controller;

    @Autowired
    private BookService service;
    
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;
    
//    @MockBean
    private BookRepository bookRepository;

    
    @TestConfiguration
    static class Config {
            
        @Bean
        public ObjectMapper mapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            return mapper;
        }
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        standaloneSetup(controller);
    }
    
    @Test
    void testBook() throws Exception {
        List<Books> books = controller.getAllBooks();
        assertEquals(0, books.size());
    }
    
    @Test
    void testGetAllBooksWithREST() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BookRestController.REQUEST_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[1].title", is("Clean Code")))
            .andReturn();
        
        String jsonPayload = mvcResult.getResponse().getContentAsString();
        List<Book> books = Arrays.asList(mapper.readValue(jsonPayload, Book[].class));
        assertEquals(3,  books.size());
        assertEquals("Clean Code", books.get(1).getTitle());
    }
    
    @Test
    void testRestAssured() {
        // @formatter:off
        given()
            .log().all()
        .when()
            .get(BookRestController.REQUEST_URL)
       .then()
            .body("[1].title", equalTo("Clean Code"));        
        // @formatter:on
    }
    
    @Test
    void testWithMockito() throws Exception {
        // prepare
        List<Book> mockedBooks = new ArrayList<>();
        Mockito.when(bookRepository.findAllBooks()).thenReturn(mockedBooks);

        // execute
        List<Books> books = controller.getAllBooks();

        // test
        assertEquals(0, books.size());
        verify(bookRepository, times(1)).findAllBooks();
    }
    
    
}
