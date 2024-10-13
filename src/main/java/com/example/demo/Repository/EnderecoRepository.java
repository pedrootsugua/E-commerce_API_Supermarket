package com.example.demo.Repository;

import com.example.demo.Model.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
    @Query("SELECT e FROM EnderecoModel e WHERE e.cep = ?1")
    EnderecoModel buscaPorCep(String cep);
}
