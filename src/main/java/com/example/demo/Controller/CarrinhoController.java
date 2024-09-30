package com.example.demo.Controller;

import com.example.demo.DTO.CarrinhoAdicionarRequestDTO;
import com.example.demo.DTO.CarrinhoAdicionarResponseDTO;
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

    @PostMapping("/adicionar")
    public ResponseEntity<CarrinhoAdicionarResponseDTO> adicionarItemCarrinho(@RequestBody CarrinhoAdicionarRequestDTO dto) throws Exception {
        return carrinhoService.adicionarCarrinho(dto);
    }
}
