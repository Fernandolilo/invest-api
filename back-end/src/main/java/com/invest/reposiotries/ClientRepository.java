package com.invest.reposiotries;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invest.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>{

	Optional<Client> findByEmail(String email);
}
