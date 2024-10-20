package com.example.demo.Repository;

import com.example.demo.Model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<ClienteModel, Long> {
    @Query("SELECT c FROM ClienteModel c WHERE c.nome = ?1")
    ClienteModel buscaPorNome(String nome);
    @Query("SELECT c FROM ClienteModel c WHERE c.cpf = ?1")
    ClienteModel buscaPorCPF(String cpf);
}
