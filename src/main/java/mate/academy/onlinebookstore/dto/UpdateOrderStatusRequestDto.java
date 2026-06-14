package mate.academy.onlinebookstore.dto;

import jakarta.validation.constraints.NotNull;
import mate.academy.onlinebookstore.model.Status;

public record UpdateOrderStatusRequestDto(
        @NotNull
        Status status
) {
}
