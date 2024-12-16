package mate.academy.onlinebookstore.mapper;

import mate.academy.onlinebookstore.config.MapperConfig;
import mate.academy.onlinebookstore.dto.BookDto;
import mate.academy.onlinebookstore.dto.CreateBookRequestDto;
import mate.academy.onlinebookstore.dto.UpdateBookRequestDto;
import mate.academy.onlinebookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toEntity(CreateBookRequestDto bookDto);

    @Mapping(target = "title", source = "updateRequest.title")
    @Mapping(target = "author", source = "updateRequest.author")
    @Mapping(target = "isbn", source = "updateRequest.isbn")
    @Mapping(target = "price", source = "updateRequest.price")
    @Mapping(target = "description", source = "updateRequest.description")
    @Mapping(target = "coverImage", source = "updateRequest.coverImage")
    @Mapping(target = "deleted", ignore = true)
    Book updateBook(Book book, UpdateBookRequestDto updateRequest);
}
