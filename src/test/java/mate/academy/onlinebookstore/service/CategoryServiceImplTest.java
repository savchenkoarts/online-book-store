package mate.academy.onlinebookstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.academy.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstore.dto.CategoryCreateRequestDto;
import mate.academy.onlinebookstore.dto.CategoryDto;
import mate.academy.onlinebookstore.dto.CategoryUpdateRequestDto;
import mate.academy.onlinebookstore.exception.EntityNotFoundException;
import mate.academy.onlinebookstore.mapper.BookMapper;
import mate.academy.onlinebookstore.mapper.CategoryMapper;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.model.Category;
import mate.academy.onlinebookstore.repository.BookRepository;
import mate.academy.onlinebookstore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(
                categoryRepository,
                categoryMapper,
                bookRepository,
                bookMapper
        );
    }

    @Test
    void findAll_ExistingCategories_ReturnsMappedDtos() {
        Category category = new Category();
        CategoryDto dto = new CategoryDto();

        when(categoryRepository.findAll())
                .thenReturn(List.of(category));

        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        List<CategoryDto> actual = categoryService.findAll();

        assertThat(actual)
                .containsExactly(dto);

        verify(categoryRepository)
                .findAll();

        verify(categoryMapper)
                .toDto(category);
    }

    @Test
    void getById_ExistingCategory_ReturnsDto() {
        Category category = new Category();
        CategoryDto dto = new CategoryDto();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        CategoryDto actual = categoryService.getById(1L);

        assertThat(actual)
                .isSameAs(dto);

        verify(categoryRepository)
                .findById(1L);

        verify(categoryMapper)
                .toDto(category);
    }

    @Test
    void getById_MissingCategory_ThrowsException() {
        when(categoryRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("99");

        verify(categoryRepository)
                .findById(99L);
    }

    @Test
    void save_ValidRequest_ReturnsSavedCategory() {
        CategoryCreateRequestDto request =
                new CategoryCreateRequestDto(
                        "Fiction",
                        "Fiction books"
                );

        Category category = new Category();
        CategoryDto dto = new CategoryDto();

        when(categoryMapper.toEntity(request))
                .thenReturn(category);

        when(categoryRepository.save(category))
                .thenReturn(category);

        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        CategoryDto actual = categoryService.save(request);

        assertThat(actual)
                .isSameAs(dto);

        verify(categoryRepository)
                .save(category);
    }

    @Test
    void update_ExistingCategory_MapsAndSavesCategory() {
        CategoryUpdateRequestDto request =
                new CategoryUpdateRequestDto(
                        "Novels",
                        "Novel books"
                );

        Category category = new Category();
        CategoryDto dto = new CategoryDto();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(categoryRepository.save(category))
                .thenReturn(category);

        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        CategoryDto actual = categoryService.update(1L, request);

        assertThat(actual)
                .isSameAs(dto);

        verify(categoryMapper)
                .updateCategoryFromDto(request, category);

        verify(categoryRepository)
                .save(category);
    }

    @Test
    void getBooksByCategoryId_MatchingBook_ReturnsMappedBooks() {
        Book book = new Book();
        BookDtoWithoutCategoryIds dto =
                new BookDtoWithoutCategoryIds();

        when(bookRepository.findAllByCategoriesId(1L))
                .thenReturn(List.of(book));

        when(bookMapper.toDtoWithoutCategories(book))
                .thenReturn(dto);

        List<BookDtoWithoutCategoryIds> actual =
                categoryService.getBooksByCategoryId(1L);

        assertThat(actual)
                .containsExactly(dto);

        verify(bookRepository)
                .findAllByCategoriesId(1L);

        verify(bookMapper)
                .toDtoWithoutCategories(book);
    }

    @Test
    void deleteById_ExistingId_DelegatesToRepository() {
        categoryService.deleteById(1L);

        verify(categoryRepository)
                .deleteById(1L);
    }
}
