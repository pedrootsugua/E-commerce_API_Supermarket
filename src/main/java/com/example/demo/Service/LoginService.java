package com.example.demo.Service;

import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Repository.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private CredencialRepository credencialRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<Boolean> entrar(CredencialDTO credencialDTO) {
        CredencialModel credencial = credencialRepository.buscaPorEmail(credencialDTO.getEmail());
        if (credencial != null) {
            boolean senha = passwordService.verificarSenha(credencialDTO.getSenha(), credencial.getSenha());
            if (senha) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
