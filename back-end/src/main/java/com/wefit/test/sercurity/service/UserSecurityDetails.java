package com.wefit.test.sercurity.service;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurityDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Você pode implementar lógica real se quiser controlar expiração
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode vincular a um campo tipo "bloqueado"
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Para expiração de senha, pode controlar por data
    }

    @Override
    public boolean isEnabled() {
        return true; // Ou usar um campo boolean como "ativo"
    }
}
