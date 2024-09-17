package com.example.demo.Controller;

import com.example.demo.DTO.ProdutoAlterarDTO;
import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.DTO.ProdutoRetornoDTO;
import com.example.demo.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/listagem")
    public ResponseEntity<Map<String, Object>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoService.listarProdutos(page, size);
    }
    @GetMapping("/buscaID")
    public ResponseEntity<ProdutoAlterarDTO> listarProdutos(
            @RequestParam("id") Long id){
        return produtoService.buscarProduto(id);
    }
}