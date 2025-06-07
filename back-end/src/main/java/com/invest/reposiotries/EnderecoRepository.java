package com.invest.reposiotries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invest.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

}
