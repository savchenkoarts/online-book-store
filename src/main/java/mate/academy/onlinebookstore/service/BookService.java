package mate.academy.onlinebookstore.service;

import mate.academy.onlinebookstore.dto.BookCreateRequestDto;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.BookSearchParametersDto;
import mate.academy.onlinebookstore.dto.BookUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getAllBooks(Pageable pageable);

    Page<BookDto> searchBooks(BookSearchParametersDto searchParametersDto, Pageable pageable);

    BookDto getBookById(Long id);

    BookDto createBook(BookCreateRequestDto bookCreateRequestDto);

    void deleteById(Long id);

    BookDto updateBook(Long id, BookUpdateRequestDto updateRequest);
}
