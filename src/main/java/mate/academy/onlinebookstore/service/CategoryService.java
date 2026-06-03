package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstore.dto.CategoryCreateRequestDto;
import mate.academy.onlinebookstore.dto.CategoryDto;
import mate.academy.onlinebookstore.dto.CategoryUpdateRequestDto;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CategoryCreateRequestDto requestDto);

    CategoryDto update(Long id, CategoryUpdateRequestDto requestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
