package com.example.demo.Model;

import com.example.demo.DTO.CadastroClienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "endereco")
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String uf;
    @Column(nullable = false)
    private Boolean entrega;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clienteId")
    private ClienteModel clienteId;

    public EnderecoModel(CadastroClienteDTO dto, ClienteModel cliente) {
        this.cep = dto.getCep();
        this.logradouro = dto.getLogradouro();
        this.complemento = dto.getComplemento();
        this.bairro = dto.getBairro();
        this.numero = dto.getNumero();
        this.cidade = dto.getCidade();
        this.uf = dto.getUf();
        this.clienteId = cliente;
        this.entrega = true;
    }
}