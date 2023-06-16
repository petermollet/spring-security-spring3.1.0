package fr.mollet.springsecurity.dto.responses;

import java.util.Set;

/**
 * DTO for {@link fr.mollet.springsecurity.domains.User}
 */
public record UserInfo(
        Long id,
        String username,
        String email,
        Set<String> role
){
}