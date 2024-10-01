package com.example.demo.Controller;

import com.example.demo.DTO.CarrinhoBuscarNLRequestDTO;
import com.example.demo.DTO.CarrinhoBuscarNLResponseDTO;
import com.example.demo.Service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/buscarCarrinhoNL")
    public ResponseEntity<CarrinhoBuscarNLResponseDTO> buscarProdutosCarrinhoNL(@RequestBody CarrinhoBuscarNLRequestDTO dto) throws Exception {
        return carrinhoService.buscarCarrinhoNL(dto);
    }
}
