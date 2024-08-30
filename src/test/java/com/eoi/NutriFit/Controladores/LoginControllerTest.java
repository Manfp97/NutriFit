package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testLogin_NoError_ReturnsLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    void testLogin_WithError_ReturnsLoginPageWithErrorMessage() throws Exception {
        mockMvc.perform(get("/login")
                        .param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("msg", "Usuario o contraseña incorrectos"));
    }

    @Test
    void testProcessLogin_CorrectCredentials_RedirectsToHome() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findUsuarioByUsernameAndActivoTrue(anyString())).thenReturn(Optional.of(usuario));
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/login")
                        .param("email", "test@example.com")
                        .param("password", "correctPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(usuarioRepository, times(1)).findUsuarioByUsernameAndActivoTrue("test@example.com");
        verify(bCryptPasswordEncoder, times(1)).matches("correctPassword", "encodedPassword");
    }

    @Test
    void testProcessLogin_IncorrectCredentials_RedirectsToLoginWithError() throws Exception {
        when(usuarioRepository.findUsuarioByUsernameAndActivoTrue(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .param("email", "test@example.com")
                        .param("password", "wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));

        verify(usuarioRepository, times(1)).findUsuarioByUsernameAndActivoTrue("test@example.com");
        verify(bCryptPasswordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testProcessLogin_WrongPassword_RedirectsToLoginWithError() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findUsuarioByUsernameAndActivoTrue(anyString())).thenReturn(Optional.of(usuario));
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("email", "test@example.com")
                        .param("password", "wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true"));

        verify(usuarioRepository, times(1)).findUsuarioByUsernameAndActivoTrue("test@example.com");
        verify(bCryptPasswordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
    }
}
