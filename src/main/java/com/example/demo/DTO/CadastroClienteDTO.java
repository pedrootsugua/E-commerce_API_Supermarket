package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CadastroClienteDTO {
    private String nome;
    private String email;
    private String senha;
    private String genero;
    private String cpf;
    private Date dtNascimento;
    List<EnderecoDTO> enderecos;
}
