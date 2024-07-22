package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testUserLoginSuccess() throws Exception {
        Usuario user = new Usuario();
        user.setPassword("testPassword");
        when(usuarioRepository.findBynombreDeUsuario(eq("test@example.com"))).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        mockMvc.perform(post("/login").param("email", "test@example.com").param("password", "testPassword"))
                .andDo(print())
                .andExpect(redirectedUrl("/"));

        verify(usuarioRepository).findBynombreDeUsuario(eq("test@example.com"));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testUserLoginFail() throws Exception {
        when(usuarioRepository.findBynombreDeUsuario(eq("test@example.com"))).thenReturn(Optional.empty());

        mockMvc.perform(post("/login").param("email", "test@example.com").param("password", "incorrectPassword"))
                .andDo(print())
                .andExpect(redirectedUrl("/login?error=true"));

        verify(usuarioRepository).findBynombreDeUsuario(eq("test@example.com"));
        verify(authenticationManager, times(0)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}