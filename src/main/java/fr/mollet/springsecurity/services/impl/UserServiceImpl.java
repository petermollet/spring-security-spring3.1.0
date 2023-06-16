package fr.mollet.springsecurity.services.impl;

import fr.mollet.springsecurity.config.Constants;
import fr.mollet.springsecurity.domains.Role;
import fr.mollet.springsecurity.domains.User;
import fr.mollet.springsecurity.dto.requests.RegisterRequest;
import fr.mollet.springsecurity.repositories.RoleRepository;
import fr.mollet.springsecurity.repositories.UserRepository;
import fr.mollet.springsecurity.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findEntityByUsername(String username) {
        log.debug("SERVICE to find user by username : {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isUsernameTaken(String username) {
        log.debug("SERVICE to check if username is taken : {}", username);
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isEmailTaken(String email) {
        log.debug("SERVICE to check if email is taken : {}", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public Long register(RegisterRequest registerRequest) {
        log.debug("SERVICE to register : {}", registerRequest);
        User user = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        Set<String> rolesStr = registerRequest.roles();
        Set<Role> roles = new HashSet<>();

        if (rolesStr == null || rolesStr.isEmpty())
            roles.add(getRoleByNameOrThrow(Constants.ROLE_USER));
        else
            rolesStr.forEach(role -> roles.add(getRoleByNameOrThrow(role)));

        user.setRoles(roles);
        return userRepository.save(user).getId();
    }

    private Role getRoleByNameOrThrow(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")); //TODO: Add Custom exception and handler
    }

}
