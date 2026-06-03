package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequestDto(
        @NotBlank
        String name,
        String description
) {
}
