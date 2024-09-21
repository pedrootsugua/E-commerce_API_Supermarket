package com.example.demo.Repository;

import com.example.demo.Model.URLImagensModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface URLImagensRepository extends JpaRepository<URLImagensModel, Long> {
    URLImagensModel findByUrl(String url);
    @Query("SELECT u FROM URLImagensModel u WHERE u.padrao = true AND u.produtoId.id = :produtoId")
    URLImagensModel findPadrao(Long produtoId);
}