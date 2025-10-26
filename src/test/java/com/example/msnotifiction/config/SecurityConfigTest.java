package com.example.msnotifiction.config;

import com.example.msnotifiction.jwt.JwtRequestFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private HttpSecurity httpSecurity;

    @Test
    void shouldConfigureSecurity() throws Exception {
        // Given
        SecurityConfig securityConfig = new SecurityConfig(jwtRequestFilter);
        
        // Mock setup
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
        SecurityFilterChain mockChain = mock(SecurityFilterChain.class);
        when(httpSecurity.build()).thenReturn((DefaultSecurityFilterChain) mockChain);

        // When
        SecurityFilterChain filterChain = securityConfig.securityFilterChain(httpSecurity);

        // Then
        assert filterChain != null;
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).addFilterBefore(any(), any());
        verify(httpSecurity).build();
    }

    @Test
    void shouldCreateSecurityConfig() {
        // When
        SecurityConfig securityConfig = new SecurityConfig(jwtRequestFilter);

        // Then
        assert securityConfig != null;
    }
}