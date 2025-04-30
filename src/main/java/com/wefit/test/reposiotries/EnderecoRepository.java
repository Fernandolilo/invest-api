package com.wefit.test.reposiotries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wefit.test.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

}
