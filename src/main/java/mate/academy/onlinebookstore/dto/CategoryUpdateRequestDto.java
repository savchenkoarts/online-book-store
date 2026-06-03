package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequestDto(
        @NotBlank
        String name,
        String description
) {
}
