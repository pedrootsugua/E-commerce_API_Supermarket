package com.example.demo.Controller;

import com.example.demo.DTO.UsuarioCredencialDTO;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarClientes() {
        return usuarioService.listarUsuarios();
    }

    @PatchMapping("/desativar/{id}")
    public void desativarUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.desativarUsuario(id);
    }

    @PatchMapping("/ativar/{id}")
    public void ativarUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.ativarUsuario(id);
    }
}
