package mate.academy.onlinebookstore.service;

import mate.academy.onlinebookstore.dto.UserDto;
import mate.academy.onlinebookstore.dto.UserRegistrationRequestDto;

public interface UserService {
    UserDto register(UserRegistrationRequestDto request);
}
