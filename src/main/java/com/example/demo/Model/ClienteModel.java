package com.example.demo.Model;

import com.example.demo.DTO.CadastroClienteDTO;
import jakarta.persistence.*;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cliente")
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = true, name = "dt_nascimento")
    private Date dtNascimento;

    public ClienteModel(CadastroClienteDTO dto) {
        this.nome = dto.getNome();
        this.telefone = dto.getTelefone();
        this.cpf = dto.getCpf();
        this.dtNascimento = dto.getDtNascimento();
    }
    private Date ajustarData(Date data) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        String dataFormatada = formatter.format(data);

        return formatter.parse(dataFormatada);
    }
}