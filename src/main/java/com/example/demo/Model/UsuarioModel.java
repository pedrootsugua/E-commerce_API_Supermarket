package com.example.demo.Model;

import com.example.demo.DTO.UsuarioCredencialDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "usuario")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String grupo;

    public UsuarioModel(UsuarioCredencialDTO dto){
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.grupo = dto.getGrupo();
    }
}
