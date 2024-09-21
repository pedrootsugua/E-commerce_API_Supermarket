package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ProdutoAlterarRequestDTO {
    private String nomeProduto;
    private String descricao;
    private double preco;
    private int quantidade;
    private String categoria;
    private String marca;
    private double avaliacao;
    @JsonIgnore
    private MultipartFile imagemPrincipal;
    @JsonIgnore
    private List<MultipartFile> imagensNovas;
    private List<String> urlImagensExcluidas;

}
