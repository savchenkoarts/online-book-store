package mate.academy.onlinebookstore.specification;

import java.util.List;
import java.util.Map;
import mate.academy.onlinebookstore.dto.BookSearchParametersDto;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationProvider {
    public Map<String, List<String>> getSearchParametersDto(BookSearchParametersDto param) {
        return Map.of(
                "title", param.titles(),
                "author", param.authors(),
                "isbn", param.isbn()
        );
    }
}
