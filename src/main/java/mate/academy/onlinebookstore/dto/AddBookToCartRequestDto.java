package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddBookToCartRequestDto(
        @NotNull
        @Positive
        Long bookId,

        @Positive
        int quantity
) {
}
