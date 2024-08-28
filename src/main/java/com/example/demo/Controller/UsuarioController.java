package com.example.demo.Controller;

import com.example.demo.DTO.UsuarioCredencialDTO;
import com.example.demo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioCredencialDTO> criarCliente(@RequestBody UsuarioCredencialDTO dto) throws ParseException {
        return usuarioService.cadastrarUsuario(dto);
    }
}
