package com.example.demo.Service;

import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Model.CredencialModel;
import com.example.demo.Repository.CredencialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Mock
    private CredencialRepository credencialRepositoryTest;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEntrar_Success() {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        CredencialModel credencialModel = new CredencialModel();
        credencialModel.setEmail("test@example.com");
        credencialModel.setSenha("hashedPassword");

        when(credencialRepositoryTest.buscaPorEmail(anyString())).thenReturn(credencialModel);
        when(passwordService.verificarSenha(anyString(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = loginService.entrar(credencialDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testEntrar_Failure() {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        when(credencialRepositoryTest.buscaPorEmail(anyString())).thenReturn(null);

        ResponseEntity<Boolean> response = loginService.entrar(credencialDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
    }
}