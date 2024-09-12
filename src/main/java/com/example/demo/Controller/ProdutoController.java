package com.example.demo.Controller;

import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastro")
    public ResponseEntity<ProdutoDTO> cadastrarProduto(
            @RequestPart("produto") ProdutoDTO dto,
            @RequestPart("imagemPrincipal") MultipartFile imagemPrincipal,
            @RequestPart("imagens") List<MultipartFile> imagens) throws Exception {
        dto.setImagemPrincipal(imagemPrincipal);
        dto.setImagens(imagens);
        return produtoService.cadastrarProduto(dto);
    }
}