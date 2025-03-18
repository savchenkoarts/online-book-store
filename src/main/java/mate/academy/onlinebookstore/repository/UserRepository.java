package mate.academy.onlinebookstore.repository;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import mate.academy.onlinebookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(@NotBlank String email);
}
