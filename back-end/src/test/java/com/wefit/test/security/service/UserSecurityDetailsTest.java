package com.wefit.test.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import com.wefit.test.entity.enums.Role;
import com.wefit.test.sercurity.service.UserSecurityDetails;

@ActiveProfiles("test")
public class UserSecurityDetailsTest {

    private UUID id;
    private String email;
    private String senha;
    private Set<Role> roles;
    private UserSecurityDetails userDetails;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        email = "test@example.com";
        senha = "123456";
        roles = Set.of(Role.ADMIN, Role.USER);

        userDetails = new UserSecurityDetails(id, email, senha, roles);
    }

    @Test
    public void testUserDetailsFields() {
        assertEquals(id, userDetails.getId());
        assertEquals(email, userDetails.getUsername());
        assertEquals(senha, userDetails.getPassword());
    }

    @Test
    public void testAuthoritiesMapping() {
        Set<String> authorities = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(java.util.stream.Collectors.toSet());

        assertTrue(authorities.contains("ROLE_ADMIN"));
        assertTrue(authorities.contains("ROLE_USER"));
    }

    @Test
    public void testHasRole() {
        assertTrue(userDetails.hasRole(Role.ADMIN));
        assertTrue(userDetails.hasRole(Role.USER));
        assertTrue(userDetails.hasRole(Role.ADMIN, Role.USER));
    }

    @Test
    public void testAccountNonExpiredAndEnabled() {
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

}
