package mate.academy.onlinebookstore.service;

import mate.academy.onlinebookstore.dto.AddBookToCartRequestDto;
import mate.academy.onlinebookstore.dto.ShoppingCartDto;
import mate.academy.onlinebookstore.dto.UpdateCartItemRequestDto;
import mate.academy.onlinebookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    ShoppingCartDto addBookToCart(Long userId, AddBookToCartRequestDto requestDto);

    ShoppingCartDto updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemRequestDto requestDto
    );

    void deleteCartItem(Long userId, Long cartItemId);

    void createShoppingCart(User user);
}
