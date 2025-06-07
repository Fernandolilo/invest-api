package com.invest.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.invest.entity.enums.Perfil;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoPessoa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nome;
	private String cpfOuCnpj;
	private String celular;
	private String telefone;
	private String email;
	private TipoPessoa tipo;
	private Perfil perfil;
	private Role role;
	private String senha;
	private boolean confirme;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ROLES")
	private Set<Integer> roles = new HashSet<>();

	@OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Endereco endereco;
	
	public Set<Role> getRoles() {
	    if (roles == null) {
	        return new HashSet<>();
	    }
	    return roles.stream().map(Role::toEnum).collect(Collectors.toSet());
	}
	
	public void addRole(Role role) {
		roles.add(role.getCod());
	}

}
