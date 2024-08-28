package com.example.demo.Service;

import com.example.demo.DTO.UsuarioCredencialDTO;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.CredencialRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    private PasswordService passwordService = new PasswordService();
    public ResponseEntity<UsuarioCredencialDTO> cadastrarUsuario(UsuarioCredencialDTO dto) throws ParseException {

        try {
            CredencialModel usuarioExistente = credencialRepository.buscaPorEmail(dto.getEmail());

            if(usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            UsuarioModel usuarioSalvo = usuarioRepository.save(new UsuarioModel(dto));

            CredencialModel credencialModel = new CredencialModel();
            credencialModel.setEmail(dto.getEmail());
            credencialModel.setSenha(passwordService.criptografar(dto.getSenha()));
            credencialModel.setIdUsuario(usuarioSalvo.getId());
            credencialRepository.save(credencialModel);

            dto.setSenha("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean login(String email, String senha) {
        CredencialModel credencial = credencialRepository.buscaPorEmail(email);
        if (credencial != null) {
            return passwordService.verificarSenha(senha, credencial.getSenha());
        }
        return false;
    }
}