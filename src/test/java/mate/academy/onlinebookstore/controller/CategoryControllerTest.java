package mate.academy.onlinebookstore.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mate.academy.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.onlinebookstore.dto.CategoryCreateRequestDto;
import mate.academy.onlinebookstore.dto.CategoryDto;
import mate.academy.onlinebookstore.dto.CategoryUpdateRequestDto;
import mate.academy.onlinebookstore.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryController(categoryService))
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_ExistingCategories_ReturnsCategories() throws Exception {
        CategoryDto dto = categoryDto(1L, "Fiction");

        when(categoryService.findAll())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Fiction"));

        verify(categoryService)
                .findAll();
    }

    @Test
    void getCategoryById_ExistingCategory_ReturnsCategory()
            throws Exception {

        when(categoryService.getById(1L))
                .thenReturn(categoryDto(1L, "Fiction"));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(1));

        verify(categoryService)
                .getById(1L);
    }

    @Test
    void createCategory_ValidRequest_ReturnsCreatedCategory()
            throws Exception {

        CategoryCreateRequestDto request =
                new CategoryCreateRequestDto(
                        "Fiction",
                        "Fiction books"
                );

        when(categoryService.save(request))
                .thenReturn(categoryDto(1L, "Fiction"));

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Fiction"));

        verify(categoryService)
                .save(request);
    }

    @Test
    void updateCategory_ExistingCategory_ReturnsUpdatedCategory()
            throws Exception {

        CategoryUpdateRequestDto request =
                new CategoryUpdateRequestDto(
                        "New Fiction",
                        "Updated books"
                );

        when(categoryService.update(1L, request))
                .thenReturn(categoryDto(1L, "New Fiction"));

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("New Fiction"));

        verify(categoryService)
                .update(1L, request);
    }

    @Test
    void getBooksByCategoryId_MatchingBook_ReturnsBooks()
            throws Exception {

        BookDtoWithoutCategoryIds book =
                new BookDtoWithoutCategoryIds();

        book.setTitle("Clean Code");

        when(categoryService.getBooksByCategoryId(1L))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/categories/1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Clean Code"));

        verify(categoryService)
                .getBooksByCategoryId(1L);
    }

    @Test
    void deleteCategory_ExistingCategory_ReturnsNoContent()
            throws Exception {

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService)
                .deleteById(1L);
    }

    private CategoryDto categoryDto(Long id, String name) {
        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
