package mate.academy.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.onlinebookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderIdAndOrderUserId(Long orderId, Long userId);

    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(
            Long itemId,
            Long orderId,
            Long userId
    );
}
