package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import com.eoi.NutriFit.Entidades.Usuario;
import com.eoi.NutriFit.Repositorios.UsuarioRepository;
import com.eoi.NutriFit.Servicios.EmailService;
import com.eoi.NutriFit.Servicios.NotificationServiceEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    //@Mock
    //private NotificationServiceEmail notificationServiceEmail;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testShowForgotPasswordForm() throws Exception {
        mockMvc.perform(get("/auth/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password-form"));
    }

    @Test
    void testForgotPasswordUserExists() throws Exception {
        DetalleUsuario detalleUsuario = new DetalleUsuario();
        detalleUsuario.setEmail("test@example.com");

        Usuario usuario = new Usuario();
        usuario.setDetalleUsuario(detalleUsuario);
        usuario.setResetToken("resetToken");

        when(usuarioRepository.findActiveUserByEmail(anyString())).thenReturn(Optional.of(usuario));
        //doNothing().when(notificationServiceEmail).sendPasswordResetEmail(anyString(), anyString());

        mockMvc.perform(post("/auth/forgot-password")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password-confirmation"));

        verify(usuarioRepository).findActiveUserByEmail("test@example.com");
        verify(usuarioRepository).save(any(Usuario.class));
        //verify(notificationServiceEmail).sendPasswordResetEmail(eq("test@example.com"), anyString());
    }

    @Test
    void testForgotPasswordUserNotFound() throws Exception {
        when(usuarioRepository.findActiveUserByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/forgot-password")
                        .param("email", "nonexistent@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("forgot-password-confirmation"));

        verify(usuarioRepository).findActiveUserByEmail("nonexistent@example.com");
        //verify(notificationServiceEmail, never()).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    void testShowResetPasswordFormValidToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setResetToken("validToken");

        when(usuarioRepository.findByResetToken("validToken")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/auth/reset-password")
                        .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password-form"))
                .andExpect(model().attributeExists("token"));

        verify(usuarioRepository).findByResetToken("validToken");
    }

    @Test
    void testShowResetPasswordFormInvalidToken() throws Exception {
        when(usuarioRepository.findByResetToken(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/auth/reset-password")
                        .param("token", "invalidToken"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));

        verify(usuarioRepository).findByResetToken("invalidToken");
    }

    @Test
    void testResetPasswordValidToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setResetToken("validToken");
        usuario.setTokenExpiration(LocalDateTime.now().plusHours(1));

        when(usuarioRepository.findByResetToken("validToken")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        mockMvc.perform(post("/auth/reset-password")
                        .param("token", "validToken")
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("reset-password-success"))
                .andExpect(model().attributeExists("message"));

        verify(usuarioRepository).findByResetToken("validToken");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testResetPasswordExpiredToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setResetToken("validToken");
        usuario.setTokenExpiration(LocalDateTime.now().minusHours(1));

        when(usuarioRepository.findByResetToken("validToken")).thenReturn(Optional.of(usuario));

        mockMvc.perform(post("/auth/reset-password")
                        .param("token", "validToken")
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));

        verify(usuarioRepository).findByResetToken("validToken");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testResetPasswordInvalidToken() throws Exception {
        when(usuarioRepository.findByResetToken(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/reset-password")
                        .param("token", "invalidToken")
                        .param("newPassword", "newPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("error"));

        verify(usuarioRepository).findByResetToken("invalidToken");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
