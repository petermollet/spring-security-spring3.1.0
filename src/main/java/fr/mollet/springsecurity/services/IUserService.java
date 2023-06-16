package fr.mollet.springsecurity.services;

import fr.mollet.springsecurity.domains.User;
import fr.mollet.springsecurity.dto.requests.RegisterRequest;

import java.util.Optional;

public interface IUserService {
    Optional<User> findEntityByUsername(String username);

    Boolean isUsernameTaken(String username);

    Boolean isEmailTaken(String email);

    Long register(RegisterRequest registerRequest);
}
