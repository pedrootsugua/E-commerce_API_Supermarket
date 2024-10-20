package com.example.demo.Controller;

import com.example.demo.DTO.AlterarClienteDTO;
import com.example.demo.DTO.CadastroClienteDTO;
import com.example.demo.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastro")
    public ResponseEntity<CadastroClienteDTO> criarCliente(@RequestBody CadastroClienteDTO dto) throws ParseException {
        return clienteService.cadastrarCliente(dto);
    }

    @PutMapping("/alterar")
    public ResponseEntity<AlterarClienteDTO> alterarCliente(@RequestBody AlterarClienteDTO dto) throws ParseException {
        return clienteService.alterarCliente(dto);
    }
}
