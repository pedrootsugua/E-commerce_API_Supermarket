package com.example.demo.Controller;

import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/entrar")
    public ResponseEntity<Boolean> acessarUsuario (@RequestBody CredencialDTO dto) throws ParseException {
        return loginService.entrar(dto);
    }
}
