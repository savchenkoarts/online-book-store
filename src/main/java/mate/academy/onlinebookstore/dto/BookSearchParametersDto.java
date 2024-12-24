package mate.academy.onlinebookstore.dto;

import java.util.List;

public record BookSearchParametersDto(
        List<String> titles,
        List<String> authors,
        List<String> isbn) {

}
