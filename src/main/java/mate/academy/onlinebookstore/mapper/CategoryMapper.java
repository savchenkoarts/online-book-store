package mate.academy.onlinebookstore.mapper;

import mate.academy.onlinebookstore.config.MapperConfig;
import mate.academy.onlinebookstore.dto.CategoryCreateRequestDto;
import mate.academy.onlinebookstore.dto.CategoryDto;
import mate.academy.onlinebookstore.dto.CategoryUpdateRequestDto;
import mate.academy.onlinebookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toEntity(CategoryCreateRequestDto requestDto);

    void updateCategoryFromDto(
            CategoryUpdateRequestDto requestDto,
            @MappingTarget Category category
    );
}
