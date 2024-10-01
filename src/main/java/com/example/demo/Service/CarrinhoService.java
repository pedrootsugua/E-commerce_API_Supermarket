// src/main/java/com/example/demo/Service/CarrinhoService.java
package com.example.demo.Service;

import com.example.demo.DTO.CarrinhoBuscarNLRequestDTO;
import com.example.demo.DTO.CarrinhoBuscarNLResponseDTO;
import com.example.demo.DTO.ProdutoQuantidadeDTO;
import com.example.demo.Model.ProdutoModel;
import com.example.demo.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<CarrinhoBuscarNLResponseDTO> buscarCarrinhoNL(CarrinhoBuscarNLRequestDTO dto) {
        Map<String, Integer> elementCountMap = countOccurrences(dto.getListaIds());
        List<ProdutoModel> produtos = produtoRepository.findByIdIn(new ArrayList<>(elementCountMap.keySet()));

        List<ProdutoQuantidadeDTO> produtosQuantidade = produtos.stream()
                .map(produto -> new ProdutoQuantidadeDTO(produto, elementCountMap.get(produto.getId().toString())))
                .collect(Collectors.toList());

        CarrinhoBuscarNLResponseDTO response = new CarrinhoBuscarNLResponseDTO(produtosQuantidade);
        return ResponseEntity.ok(response);
    }

    public static Map<String, Integer> countOccurrences(List<String> list) {
        Map<String, Integer> elementCountMap = new HashMap<>();

        for (String element : list) {
            elementCountMap.put(element, elementCountMap.getOrDefault(element, 0) + 1);
        }

        return elementCountMap;
    }
}