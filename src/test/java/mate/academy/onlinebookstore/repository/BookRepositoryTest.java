package mate.academy.onlinebookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import mate.academy.onlinebookstore.config.TestcontainersConfiguration;
import mate.academy.onlinebookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@Sql("classpath:test-data.sql")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllByCategoriesId_WithExistingCategory_ReturnsBooks() {
        var actual = bookRepository.findAllByCategoriesId(3L);

        assertThat(actual)
                .hasSize(1)
                .extracting(Book::getTitle)
                .containsExactly("Effective Java");
    }
}
