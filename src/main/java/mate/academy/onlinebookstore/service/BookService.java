package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.CreateBookRequestDto;
import mate.academy.onlinebookstore.dto.UpdateBookRequestDto;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto createBookRequestDto);

    void deleteById(Long id);

    BookDto updateBook(Long id, UpdateBookRequestDto updateRequest);
}
