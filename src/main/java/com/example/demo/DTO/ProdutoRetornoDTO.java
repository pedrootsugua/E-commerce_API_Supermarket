package com.example.demo.DTO;

import com.example.demo.Model.URLImagensModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ProdutoRetornoDTO {
    private Long id;
    private String nomeProduto;
    private double preco;
    private int quantidade;
    private Boolean ativo;
    private String urlImagemPrincipal;

    public ProdutoRetornoDTO(Long id, String nomeProduto, double preco, int quantidade, Boolean ativo, List<URLImagensModel> urlImagensModels) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.ativo = ativo;
        for (URLImagensModel urlImagensModel : urlImagensModels) {
            if (urlImagensModel.getPadrao()) {
                this.urlImagemPrincipal = urlImagensModel.getUrl();
            }
        }
    }
}
