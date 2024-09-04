package com.example.demo.Controller;

import com.example.demo.DTO.CredencialDTO;
import com.example.demo.Service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAcessarUsuario_Success() throws ParseException {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("password");

        when(loginService.entrar(any(CredencialDTO.class))).thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

        ResponseEntity<Boolean> response = loginController.acessarUsuario(credencialDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testAcessarUsuario_Unauthorized() throws ParseException {
        CredencialDTO credencialDTO = new CredencialDTO();
        credencialDTO.setEmail("test@example.com");
        credencialDTO.setSenha("wrongpassword");

        when(loginService.entrar(any(CredencialDTO.class))).thenReturn(new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED));

        ResponseEntity<Boolean> response = loginController.acessarUsuario(credencialDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(false, response.getBody());
    }
}