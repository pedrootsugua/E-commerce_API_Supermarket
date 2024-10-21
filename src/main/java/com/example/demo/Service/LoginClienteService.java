package com.example.demo.Service;

import com.example.demo.DTO.AutenticacaoLoginDTO;
import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Model.ClienteModel;
import com.example.demo.Model.CredencialClienteModel;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Model.UsuarioModel;
import com.example.demo.Repository.ClienteRepository;
import com.example.demo.Repository.CredencialClienteRepository;
import com.example.demo.Repository.CredencialRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginClienteService {

    @Autowired
    private CredencialClienteRepository credencialClienteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordService passwordService;

    public ResponseEntity<AutenticacaoLoginDTO> entrarCliente (CredencialDTO credencialDTO) {
        CredencialClienteModel credencial = credencialClienteRepository.buscaPorEmail(credencialDTO.getEmail());
        if (credencial != null) {
            boolean senhaCorreta = passwordService.verificarSenha(credencialDTO.getSenha(), credencial.getSenha());
            if (senhaCorreta) {
                ClienteModel cliente = clienteRepository.buscarPorId(credencial.getId());
                return new ResponseEntity<>(new AutenticacaoLoginDTO(true, cliente.getId(), cliente.getNome()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
