package com.example.demo.Repository;

import com.example.demo.Model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    @Query("SELECT p FROM ProdutoModel p WHERE p.nomeProduto = ?1")
    ProdutoModel buscaPorNome(String nomeProduto);
    @Query("SELECT p FROM ProdutoModel p WHERE p.id = ?1")
    ProdutoModel buscarPorId(Long id);
    @Query("SELECT p FROM ProdutoModel p WHERE p.ativo = true")
    List<ProdutoModel> buscarAtivos();
    @Query("SELECT p FROM ProdutoModel p WHERE p.ativo = false")
    List<ProdutoModel> buscarPorCategoria(String categoria);
    @Query("SELECT p FROM ProdutoModel p WHERE p.marca = ?1")
    List<ProdutoModel> buscarPorMarca(String marca);
}
