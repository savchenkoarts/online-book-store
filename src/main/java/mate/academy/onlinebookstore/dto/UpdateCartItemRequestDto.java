package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemRequestDto(
        @Positive
        int quantity
) {
}
