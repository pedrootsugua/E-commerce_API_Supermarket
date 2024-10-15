package com.example.demo.Service;

import com.example.demo.DTO.AlterarClienteRequestDTO;
import com.example.demo.DTO.AlterarClienteResponseDTO;
import com.example.demo.DTO.CadastroClienteDTO;
import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.Model.*;
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

            ClienteModel cliente = new ClienteModel(dto);
            ClienteModel clienteSalvo = clienteRepository.save(cliente);
            for (EnderecoDTO endereco : dto.getEnderecos()) {
                EnderecoModel enderecoModel = new EnderecoModel(endereco);
                enderecoModel.setClienteId(clienteSalvo);
                enderecoRepository.save(enderecoModel);
            }


            CredencialClienteModel credenciaClientelModel = new CredencialClienteModel();
            credenciaClientelModel.setEmail(dto.getEmail());
            credenciaClientelModel.setSenha(passwordService.criptografar(dto.getSenha()));
            credenciaClientelModel.setClienteId(clienteSalvo);
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

    public ResponseEntity<AlterarClienteResponseDTO> alterarCliente(AlterarClienteRequestDTO dto) {
        ClienteModel clienteSalvo = clienteRepository.findById(dto.getId()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        CredencialClienteModel credencialClienteSalva = credencialClienteRepository.buscaPorEmail(dto.getEmail());
        if (clienteSalvo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clienteSalvo.setNome(dto.getNome());
        return null;
    }
}