package mate.academy.onlinebookstore.service;

import mate.academy.onlinebookstore.dto.UserDto;
import mate.academy.onlinebookstore.dto.UserRegistrationRequestDto;
import mate.academy.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
