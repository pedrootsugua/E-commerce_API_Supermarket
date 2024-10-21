package com.example.demo.Controller;

import com.example.demo.DTO.AutenticacaoLoginDTO;
import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Service.LoginClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/loginCliente")
public class LoginClienteController {

    @Autowired
    private LoginClienteService loginClienteService;

    @PostMapping("/entrarCliente")
    public ResponseEntity<AutenticacaoLoginDTO> acessarCliente (@RequestBody CredencialDTO dto) throws ParseException {
        return loginClienteService.entrarCliente(dto);
    }
}
