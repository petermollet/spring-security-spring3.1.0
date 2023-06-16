package fr.mollet.springsecurity.controllers;

import fr.mollet.springsecurity.domains.User;
import fr.mollet.springsecurity.dto.requests.AuthRequest;
import fr.mollet.springsecurity.dto.requests.RegisterRequest;
import fr.mollet.springsecurity.dto.responses.MessageResponse;
import fr.mollet.springsecurity.security.jwt.JwtUtils;
import fr.mollet.springsecurity.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // TODO: handle CORS in a better way
public class AuthController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        log.debug("REST request to authenticate : {}", authRequest.username());
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.username(),
                authRequest.password()
        );
        authenticationManager.authenticate(authenticationToken);
        User user = userService.findEntityByUsername(authRequest.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        final String jwt = jwtUtils.generateToken(user);
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws URISyntaxException {
        log.debug("REST request to register : {}", registerRequest);

        final Boolean isUsernameTaken = userService.isUsernameTaken(registerRequest.username());
        if (Boolean.TRUE.equals(isUsernameTaken))
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken"));

        final Boolean isEmailTaken = userService.isEmailTaken(registerRequest.email());
        if (Boolean.TRUE.equals(isEmailTaken))
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already taken"));

        final Long id = userService.register(registerRequest);

        return ResponseEntity.created(new URI("/api/users/" + id))
                .body(new MessageResponse("User registered successfully"));
    }

}
