package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record BookCreateRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage,
        List<Long> categoryIds
) {}
