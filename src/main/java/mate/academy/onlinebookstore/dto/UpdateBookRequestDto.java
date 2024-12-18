package mate.academy.onlinebookstore.dto;

import java.math.BigDecimal;

public record UpdateBookRequestDto(
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage
) {}
