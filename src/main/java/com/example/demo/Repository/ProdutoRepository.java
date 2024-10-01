package com.example.demo.Repository;

import com.example.demo.Model.ProdutoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    @Query("SELECT p FROM ProdutoModel p WHERE p.nomeProduto LIKE %?1%")
    Page<ProdutoModel> buscaPorNome(String nomeProduto, Pageable pageable);

    List<ProdutoModel> findByIdIn(List<String> ids);
}
