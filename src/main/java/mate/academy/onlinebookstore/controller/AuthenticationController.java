package mate.academy.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstore.dto.UserDto;
import mate.academy.onlinebookstore.dto.UserLoginRequestDto;
import mate.academy.onlinebookstore.dto.UserLoginResponseDto;
import mate.academy.onlinebookstore.dto.UserRegistrationRequestDto;
import mate.academy.onlinebookstore.exception.RegistrationException;
import mate.academy.onlinebookstore.service.AuthenticationService;
import mate.academy.onlinebookstore.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "Creates a new user account.")
    @PostMapping("/registration")
    public UserDto registration(@Valid @RequestBody UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token.")
    @PostMapping("/login")
    public UserLoginResponseDto login(@Valid @RequestBody UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
