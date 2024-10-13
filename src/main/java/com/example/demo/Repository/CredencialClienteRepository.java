package com.example.demo.Repository;

import com.example.demo.Model.CredencialClienteModel;
import com.example.demo.Model.CredencialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialClienteRepository extends JpaRepository<CredencialClienteModel, Long> {
    @Query("SELECT u FROM CredencialModel u WHERE u.email = ?1")
    CredencialClienteModel buscaPorEmail(String email);
}
