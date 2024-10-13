package com.example.demo.Model;

import com.example.demo.DTO.CadastroClienteDTO;
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

    private String complemento;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String uf;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;

    public EnderecoModel(CadastroClienteDTO dto, ClienteModel cliente) {
        this.cep = dto.getCep();
        this.logradouro = dto.getLogradouro();
        this.complemento = dto.getComplemento();
        this.bairro = dto.getBairro();
        this.numero = dto.getNumero();
        this.cidade = dto.getCidade();
        this.uf = dto.getUf();
        this.cliente = cliente;
    }
}