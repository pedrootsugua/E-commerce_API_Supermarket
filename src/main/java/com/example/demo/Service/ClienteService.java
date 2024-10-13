package com.example.demo.Service;

import com.example.demo.DTO.CadastroClienteDTO;
import com.example.demo.Model.ClienteModel;
import com.example.demo.Model.CredencialClienteModel;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Model.EnderecoModel;
import com.example.demo.Repository.ClienteRepository;
import com.example.demo.Repository.CredencialClienteRepository;
import com.example.demo.Repository.CredencialRepository;
import com.example.demo.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CredencialClienteRepository credencialClienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private PasswordService passwordService = new PasswordService();

    public ResponseEntity<CadastroClienteDTO> cadastrarCliente(CadastroClienteDTO dto) throws ParseException {
        try {
            if (dto.getDtNascimento() != null) {
                dto.setDtNascimento(ajustarData(dto.getDtNascimento()));
            }
            CredencialClienteModel usuarioExistente = credencialClienteRepository.buscaPorEmail(dto.getEmail());

            if (usuarioExistente != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            ClienteModel usuario = new ClienteModel(dto);
            ClienteModel usuarioSalvo = clienteRepository.save(usuario);

            EnderecoModel endereco = new EnderecoModel(dto, usuarioSalvo);
            enderecoRepository.save(endereco);

            CredencialClienteModel credenciaClientelModel = new CredencialClienteModel();
            credenciaClientelModel.setEmail(dto.getEmail());
            credenciaClientelModel.setSenha(passwordService.criptografar(dto.getSenha()));
            credenciaClientelModel.setUsuario(usuarioSalvo);
            credencialClienteRepository.save(credenciaClientelModel);

            dto.setSenha("");
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Date ajustarData(Date data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        String dataFormatada = formatter.format(data);

        return formatter.parse(dataFormatada);
    }
}