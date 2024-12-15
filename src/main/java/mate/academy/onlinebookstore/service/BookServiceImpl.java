package mate.academy.onlinebookstore.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.CreateBookRequestDto;
import mate.academy.onlinebookstore.dto.UpdateBookRequestDto;
import mate.academy.onlinebookstore.exception.EntityNotFoundException;
import mate.academy.onlinebookstore.mapper.BookMapper;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto createBook(CreateBookRequestDto createBookRequestDto) {
        Book book = bookMapper.toEntity(createBookRequestDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto updateBook(Long id, UpdateBookRequestDto updateRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book with id " + id + " not found"));
        Optional.ofNullable(updateRequest.getTitle()).ifPresent(book::setTitle);
        Optional.ofNullable(updateRequest.getAuthor()).ifPresent(book::setAuthor);
        Optional.ofNullable(updateRequest.getIsbn()).ifPresent(book::setIsbn);
        Optional.ofNullable(updateRequest.getPrice()).ifPresent(book::setPrice);
        Optional.ofNullable(updateRequest.getDescription()).ifPresent(book::setDescription);
        Optional.ofNullable(updateRequest.getCoverImage()).ifPresent(book::setCoverImage);
        return bookMapper.toDto(bookRepository.save(book));
    }
}
