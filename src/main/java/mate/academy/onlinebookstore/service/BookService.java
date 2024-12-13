package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto createBookRequestDto);
}
