package mate.academy.onlinebookstore.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.onlinebookstore.dto.BookCreateRequestDto;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.exception.CustomGlobalExceptionHandler;
import mate.academy.onlinebookstore.exception.EntityNotFoundException;
import mate.academy.onlinebookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        BookController bookController =
                new BookController(bookService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_ExistingBooks_ReturnsPage() throws Exception {
        var pageable = PageRequest.of(
                0,
                10,
                Sort.by("title").ascending()
        );
        BookDto dto = bookDto(1L, "Clean Code");
        when(bookService.getAllBooks(pageable))
                .thenReturn(new PageImpl<>(
                        List.of(dto),
                        pageable,
                        1
                ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.content[0].author")
                        .value("Robert Martin"))
                .andExpect(jsonPath("$.content[0].isbn")
                        .value("9780132350884"));
    }

    @Test
    void getBookById_ExistingBook_ReturnsBook() throws Exception {
        when(bookService.getBookById(1L))
                .thenReturn(bookDto(1L, "Clean Code"));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(1))
                .andExpect(jsonPath("$.title")
                        .value("Clean Code"));

        verify(bookService)
                .getBookById(1L);
    }

    @Test
    void getBookById_NotFound_Returns404() throws Exception {
        when(bookService.getBookById(99L))
                .thenThrow(new EntityNotFoundException(
                    "Book not found with id: 99"));
        mockMvc.perform(get("/books/99"))
                .andExpect(status().isNotFound());
        verify(bookService)
                .getBookById(99L);
    }

    @Test
    void createBook_ValidRequest_ReturnsCreatedBook() throws Exception {
        BookCreateRequestDto request =
                new BookCreateRequestDto(
                    "Clean Code",
                    "Robert Martin",
                    "9780132350884",
                    BigDecimal.valueOf(35),
                    null,
                    null,
                    List.of(1L)
            );
        when(bookService.createBook(request))
                .thenReturn(bookDto(1L, "Clean Code"));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("Clean Code"))
                .andExpect(jsonPath("$.author")
                        .value("Robert Martin"))
                .andExpect(jsonPath("$.isbn")
                        .value("9780132350884"));
        verify(bookService)
                .createBook(request);
    }

    @Test
    void deleteBook_ExistingBook_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
        verify(bookService)
                .deleteById(1L);
    }

    private BookDto bookDto(Long id, String title) {
        BookDto dto = new BookDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthor("Robert Martin");
        dto.setIsbn("9780132350884");
        dto.setPrice(35.0);
        dto.setDescription("Programming book");
        dto.setCoverImage("cover.jpg");
        dto.setCategoryIds(Set.of(1L));
        return dto;
    }
}
