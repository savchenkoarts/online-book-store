package mate.academy.onlinebookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import mate.academy.onlinebookstore.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save_WithValidCategory_PersistsCategory() {
        Category category = new Category();
        category.setName("Fiction");
        category.setDescription("Fiction books");

        Category actual = categoryRepository.saveAndFlush(category);

        assertThat(actual.getId()).isNotNull();

        assertThat(categoryRepository.findById(actual.getId()))
                .hasValueSatisfying(saved ->
                        assertThat(saved.getName())
                                .isEqualTo("Fiction"));
    }
}
