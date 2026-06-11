package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddBookToCartRequestDto(
        @NotNull
        Long bookId,

        @Min(1)
        int quantity
) {
}
