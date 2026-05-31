package mate.academy.onlinebookstore.service;

import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstore.dto.UserLoginRequestDto;
import mate.academy.onlinebookstore.dto.UserLoginResponseDto;
import mate.academy.onlinebookstore.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        String token = jwtUtil.generateToken(request.email());
        return new UserLoginResponseDto(token);
    }
}
