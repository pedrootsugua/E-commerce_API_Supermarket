package com.example.demo.Service;

import com.example.demo.DTO.AlterarClienteDTO;
import com.example.demo.DTO.CadastroClienteDTO;
import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.ClienteRepository;
import com.example.demo.Repository.CredencialClienteRepository;
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

    public ResponseEntity<AlterarClienteDTO> alterarCliente(AlterarClienteDTO dto) throws ParseException {
        ClienteModel clienteSalvo = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        CredencialClienteModel credencialClienteSalva = credencialClienteRepository.buscaPorEmail(dto.getEmail());
        if (clienteSalvo == null || credencialClienteSalva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clienteSalvo.setNome(dto.getNome());
        clienteSalvo.setGenero(dto.getGenero());
        clienteSalvo.setDtNascimento(ajustarData(dto.getDtNascimento()));
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            credencialClienteSalva.setSenha(passwordService.criptografar(dto.getSenha()));
        }
        boolean enderecoEntregaTrocado = false;
        EnderecoModel antigoEnderecoPadrao;
        if (dto.getIdEnderecoPadrao() != null) {
            antigoEnderecoPadrao = enderecoRepository.findByEntrega(true);
            EnderecoModel novoEnderecoPadrao = enderecoRepository.findById(dto.getIdEnderecoPadrao()).orElseThrow(
                    () -> new RuntimeException("Endereço não encontrado!"));
            if (novoEnderecoPadrao != null && antigoEnderecoPadrao != null) {
                antigoEnderecoPadrao.setEntrega(false);
                novoEnderecoPadrao.setEntrega(true);
                enderecoRepository.save(antigoEnderecoPadrao);
                enderecoRepository.save(novoEnderecoPadrao);
                enderecoEntregaTrocado = true;
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        if (dto.getEnderecos() != null) {
            for (EnderecoDTO endereco : dto.getEnderecos()) {
                if (endereco != null) {
                    if (endereco.getEntrega()){
                        if (enderecoEntregaTrocado){
                            return ResponseEntity.status(HttpStatus.CONFLICT).build();
                        }
                        antigoEnderecoPadrao = enderecoRepository.findByEntrega(true);
                        antigoEnderecoPadrao.setEntrega(false);
                        enderecoRepository.save(antigoEnderecoPadrao);
                        enderecoEntregaTrocado = true;
                    }
                    EnderecoModel enderecoModel = new EnderecoModel(endereco);
                    enderecoModel.setClienteId(clienteSalvo);
                    enderecoRepository.save(enderecoModel);
                }
            }
        }
        clienteRepository.save(clienteSalvo);
        credencialClienteRepository.save(credencialClienteSalva);
        ClienteModel clienteAlterado = clienteRepository.findById(dto.getIdCliente()).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado!"));
        AlterarClienteDTO clienteAlteradoDTO = new AlterarClienteDTO(clienteAlterado);
        clienteAlteradoDTO.setSenha("");
        return new ResponseEntity<>(clienteAlteradoDTO, HttpStatus.OK);
    }

    public ResponseEntity<CadastroClienteDTO> buscarClientePorId(Long id) {
        ClienteModel usuario = clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado"));
        CadastroClienteDTO dto = new CadastroClienteDTO(usuario);
        dto.setSenha("");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}