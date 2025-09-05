package com.invest.sercurity.service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.invest.entity.enums.Role;

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
    private String cpfOuCnpj;
    private Collection<? extends GrantedAuthority> authorities;

    // Construtor que converte o Set<Role> em uma Collection de GrantedAuthority
    public UserSecurityDetails(UUID id, String email, String cpfOuCnpj, String senha, Set<Role> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
				.collect(Collectors.toList());
    }

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
    
    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }
    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Role... roles) {
		Set<String> authorities = getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		for (Role role : roles) {
			if (!authorities.contains(role.getDescricao())) {
				return false;
			}
		}
		return true;
	}
}
