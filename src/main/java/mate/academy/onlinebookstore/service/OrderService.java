package mate.academy.onlinebookstore.service;

import java.util.List;
import mate.academy.onlinebookstore.dto.CreateOrderRequestDto;
import mate.academy.onlinebookstore.dto.OrderDto;
import mate.academy.onlinebookstore.dto.OrderItemDto;
import mate.academy.onlinebookstore.dto.UpdateOrderStatusRequestDto;

public interface OrderService {
    OrderDto placeOrder(Long userId, CreateOrderRequestDto requestDto);

    List<OrderDto> getOrders(Long userId);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId);

    OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId);

    OrderDto updateStatus(Long orderId,
                          UpdateOrderStatusRequestDto requestDto);
}
