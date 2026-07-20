package mate.academy.onlinebookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.onlinebookstore.dto.BookCreateRequestDto;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.BookUpdateRequestDto;
import mate.academy.onlinebookstore.exception.EntityNotFoundException;
import mate.academy.onlinebookstore.mapper.BookMapper;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.model.Category;
import mate.academy.onlinebookstore.repository.BookRepository;
import mate.academy.onlinebookstore.repository.CategoryRepository;
import mate.academy.onlinebookstore.specification.BookSpecificationProvider;
import mate.academy.onlinebookstore.specification.SpecificationProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private SpecificationProvider specificationProvider;
    @Mock
    private BookSpecificationProvider bookSpecificationProvider;
    @Mock
    private CategoryRepository categoryRepository;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper,
                specificationProvider, bookSpecificationProvider, categoryRepository);
    }

    @Test
    void getAllBooks_ExistingBooks_ReturnsMappedPage() {
        var pageable = PageRequest.of(0, 10);
        Book book = new Book();
        BookDto dto = new BookDto();
        dto.setTitle("Clean Code");
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(book)).thenReturn(dto);

        var actual = bookService.getAllBooks(pageable);

        assertThat(actual.getContent()).containsExactly(dto);
    }

    @Test
    void getBookById_MissingBook_ThrowsException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createBook_ValidRequest_SavesBookWithCategories() {
        var request = new BookCreateRequestDto("Clean Code", "Robert Martin",
                "9780132350884", BigDecimal.valueOf(35), null, null, List.of(1L));
        Book book = new Book();
        Category category = new Category();
        BookDto dto = new BookDto();
        when(bookMapper.toEntity(request)).thenReturn(book);
        when(categoryRepository.findAllById(List.of(1L))).thenReturn(List.of(category));
        when(bookMapper.toDto(book)).thenReturn(dto);

        assertThat(bookService.createBook(request)).isSameAs(dto);
        assertThat(book.getCategories()).containsExactly(category);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_ExistingBook_ReturnsUpdatedBook() {
        var request = new BookUpdateRequestDto("New title", "Author", "isbn",
                BigDecimal.TEN, null, null);
        Book stored = new Book();
        Book updated = new Book();
        BookDto dto = new BookDto();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(stored));
        when(bookMapper.updateBook(stored, request)).thenReturn(updated);
        when(bookRepository.save(updated)).thenReturn(updated);
        when(bookMapper.toDto(updated)).thenReturn(dto);

        assertThat(bookService.updateBook(1L, request)).isSameAs(dto);
    }

    @Test
    void deleteById_ExistingId_DelegatesToRepository() {
        bookService.deleteById(1L);

        verify(bookRepository).deleteById(1L);
    }
}
