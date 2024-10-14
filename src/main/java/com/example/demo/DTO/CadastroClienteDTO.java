package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String numero;
    private String cidade;
    private String uf;
    private Boolean entrega;
}
