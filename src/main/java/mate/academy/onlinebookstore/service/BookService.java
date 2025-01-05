package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.BookSearchParametersDto;
import mate.academy.onlinebookstore.dto.CreateBookRequestDto;
import mate.academy.onlinebookstore.dto.UpdateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> getAllBooks(Pageable pageable);

    List<BookDto> searchBooks(BookSearchParametersDto searchParametersDto);

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto createBookRequestDto);

    void deleteById(Long id);

    BookDto updateBook(Long id, UpdateBookRequestDto updateRequest);
}
