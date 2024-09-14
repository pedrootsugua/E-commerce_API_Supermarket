package com.example.demo.DTO;

import com.example.demo.Model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UrlImagensDTO {
    private String url;
    private Boolean padrao;
}
