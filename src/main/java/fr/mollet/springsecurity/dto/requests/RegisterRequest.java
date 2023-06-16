package fr.mollet.springsecurity.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * DTO for {@link fr.mollet.springsecurity.domains.User}
 */
public record RegisterRequest(
        @Size(max = 20) @NotBlank String username,
        @Size(max = 50) @Email @NotBlank String email,
        @Size(max = 120) @NotBlank String password,
        Set<String> roles
) {
}