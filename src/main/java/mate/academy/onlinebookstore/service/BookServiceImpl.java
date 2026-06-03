package mate.academy.onlinebookstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstore.dto.BookCreateRequestDto;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.BookSearchParametersDto;
import mate.academy.onlinebookstore.dto.BookUpdateRequestDto;
import mate.academy.onlinebookstore.exception.EntityNotFoundException;
import mate.academy.onlinebookstore.mapper.BookMapper;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.model.Category;
import mate.academy.onlinebookstore.repository.BookRepository;
import mate.academy.onlinebookstore.repository.CategoryRepository;
import mate.academy.onlinebookstore.specification.BookSpecificationProvider;
import mate.academy.onlinebookstore.specification.SpecificationProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final SpecificationProvider specificationProvider;
    private final BookSpecificationProvider bookSpecificationProvider;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public Page<BookDto> searchBooks(BookSearchParametersDto param, Pageable pageable) {
        Map<String, List<String>> paramMap =
                bookSpecificationProvider.getSearchParametersDto(param);
        Specification<Book> specification = null;
        for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
            Specification<Book> sp =
                    specificationProvider.getSpecification(entry.getValue(), entry.getKey());
            specification = specification == null ? Specification.where(sp) : specification.and(sp);
        }
        Optional.ofNullable(specification).orElseThrow(() ->
                new EntityNotFoundException("No books found with search parameters " + param));
        Page<Book> bookPage = bookRepository.findAll(specification, pageable);
        return bookPage.map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto createBook(BookCreateRequestDto bookCreateRequestDto) {
        Book book = bookMapper.toEntity(bookCreateRequestDto);
        if (bookCreateRequestDto.categoryIds() != null) {
            List<Category> categories = categoryRepository
                    .findAllById(bookCreateRequestDto.categoryIds());
            book.setCategories(new HashSet<>(categories));
        }
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto updateBook(final Long id, final BookUpdateRequestDto updateRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book with id " + id + " not found"));
        Book updateBook = bookMapper.updateBook(book, updateRequest);
        return bookMapper.toDto(bookRepository.save(updateBook));
    }
}
