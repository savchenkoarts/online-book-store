package mate.academy.onlinebookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import mate.academy.onlinebookstore.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@Sql("classpath:test-data.sql")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findById_WithExistingCategory_ReturnsCategory() {
        var actual = categoryRepository.findById(3L);

        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(category ->
                        assertThat(category.getName())
                                .isEqualTo("Testing"));
    }
}
