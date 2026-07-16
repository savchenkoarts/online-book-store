package mate.academy.onlinebookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import mate.academy.onlinebookstore.model.Book;
import mate.academy.onlinebookstore.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllByCategoriesId_WithMatchingCategory_ReturnsBooks() {
        Category category = new Category();
        category.setName("Programming");
        category = categoryRepository.save(category);
        Book book = createBook("Clean Code", "9780132350884");
        book.setCategories(new HashSet<>(Set.of(category)));
        bookRepository.save(book);

        var actual = bookRepository.findAllByCategoriesId(category.getId());

        assertThat(actual)
                .hasSize(1)
                .extracting(Book::getTitle)
                .containsExactly("Clean Code");
    }

    private Book createBook(String title, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("Robert Martin");
        book.setIsbn(isbn);
        book.setPrice(BigDecimal.valueOf(35));
        return book;
    }
}
