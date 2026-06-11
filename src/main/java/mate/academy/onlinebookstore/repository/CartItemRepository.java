package mate.academy.onlinebookstore.repository;

import java.util.Optional;
import mate.academy.onlinebookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
