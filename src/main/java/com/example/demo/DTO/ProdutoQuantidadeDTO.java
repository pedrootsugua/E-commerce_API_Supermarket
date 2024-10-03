// src/main/java/com/example/demo/DTO/ProdutoQuantidadeDTO.java
package com.example.demo.DTO;

import com.example.demo.Model.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdutoQuantidadeDTO {
    private ProdutoRetornoDTO produto;
    private Integer quantidade;
}