package mate.academy.onlinebookstore.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.onlinebookstore.dto.UserDto;
import mate.academy.onlinebookstore.dto.UserRegistrationRequestDto;
import mate.academy.onlinebookstore.exception.RegistrationException;
import mate.academy.onlinebookstore.mapper.UserMapper;
import mate.academy.onlinebookstore.model.Role;
import mate.academy.onlinebookstore.model.RoleName;
import mate.academy.onlinebookstore.model.User;
import mate.academy.onlinebookstore.repository.RoleRepository;
import mate.academy.onlinebookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Can`t register user");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() ->
                        new RuntimeException("Role USER not found"));

        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
