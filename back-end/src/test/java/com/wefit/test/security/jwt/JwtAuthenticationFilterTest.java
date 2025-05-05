package com.wefit.test.security.jwt;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.wefit.test.sercurity.jwt.JwtAuthenticationFilter;
import com.wefit.test.sercurity.jwt.JwtService;
import com.wefit.test.sercurity.service.impl.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActiveProfiles("test")
public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private final String token = "valid.jwt.token";
    private final String username = "testuser";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(userDetailsServiceImpl, jwtService);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_withValidToken_setsAuthentication() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);

        UserDetails userDetails = new User(username, "password", Collections.emptyList());
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assert(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_withInvalidHeader_doesNotAuthenticate() throws Exception {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        assert(SecurityContextHolder.getContext().getAuthentication() == null);
        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoInteractions(jwtService);
    }
}
