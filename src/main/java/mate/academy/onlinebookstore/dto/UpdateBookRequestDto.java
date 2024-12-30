package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UpdateBookRequestDto(
        @NotNull
        String title,
        @NotNull
        String author,
        @NotNull
        String isbn,
        @NotNull
        @Min(0)
        BigDecimal price,
        String description,
        String coverImage
) {}
