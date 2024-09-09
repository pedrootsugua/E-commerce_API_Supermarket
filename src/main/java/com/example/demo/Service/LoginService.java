package com.example.demo.Service;

import com.example.demo.DTO.AutenticacaoLoginDTO;
import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.CredencialRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<AutenticacaoLoginDTO> entrar(CredencialDTO credencialDTO) {
        CredencialModel credencial = credencialRepository.buscaPorEmail(credencialDTO.getEmail());
        if (credencial != null) {
            boolean senhaCorreta = passwordService.verificarSenha(credencialDTO.getSenha(), credencial.getSenha());
            if (senhaCorreta) {
                UsuarioModel usuario = usuarioRepository.buscarPorId(credencial.getUsuarioId().getId());
                return new ResponseEntity<>(new AutenticacaoLoginDTO(true, usuario.getId(), usuario.getNome(), usuario.getGrupo(), usuario.isAtivo()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
