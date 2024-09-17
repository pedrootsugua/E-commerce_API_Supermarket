package com.example.demo.DTO;

import com.example.demo.Model.ProdutoModel;
import com.example.demo.Model.URLImagensModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoAlterarDTO {
    private String nomeProduto;
    private String descricao;
    private double preco;
    private int quantidade;
    private String categoria;
    private String marca;
    private String imagemPrincipal;
    private List<String> imagens;

    public ProdutoAlterarDTO(ProdutoModel produtoModel){
        this.nomeProduto = produtoModel.getNomeProduto();
        this.descricao = produtoModel.getDescricao();
        this.preco = produtoModel.getPreco();
        this.quantidade = produtoModel.getQuantidade();
        this.categoria = produtoModel.getCategoria();
        this.marca = produtoModel.getMarca();
        List<URLImagensModel> urlImagensModels = produtoModel.getUrlImagensModels();
        for (URLImagensModel urlImagensModel : produtoModel.getUrlImagensModels()) {
            if (urlImagensModel.getPadrao()) {
                this.imagemPrincipal = urlImagensModel.getUrl();
                urlImagensModels.remove(urlImagensModel);
            }
        }
        this.imagens = urlImagensModels.stream().map(URLImagensModel::getUrl).toList();    }
}
