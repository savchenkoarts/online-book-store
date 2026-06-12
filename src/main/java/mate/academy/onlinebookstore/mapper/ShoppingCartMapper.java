package mate.academy.onlinebookstore.mapper;

import mate.academy.onlinebookstore.config.MapperConfig;
import mate.academy.onlinebookstore.dto.ShoppingCartDto;
import mate.academy.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, componentModel = "spring", uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
