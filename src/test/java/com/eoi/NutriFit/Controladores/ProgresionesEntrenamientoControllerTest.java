package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.ProgresionesEntrenamiento;
import com.eoi.NutriFit.Servicios.ProgresionesEntrenamientoServi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProgresionesEntrenamientoControllerTest {

    @Mock
    private ProgresionesEntrenamientoServi service;

    @InjectMocks
    private ProgresionesEntrenamientoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
                .build();
    }

    @Test
    void testListarProgresiones_Success() throws Exception {
        // Given
        ProgresionesEntrenamiento progresion = new ProgresionesEntrenamiento();
        when(service.buscarEntidades()).thenReturn(Collections.singletonList(progresion));

        // When & Then
        mockMvc.perform(get("/progresionesEntrenamientos/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testMostrarProgresiones_Success() throws Exception {
        // Given
        ProgresionesEntrenamiento progresion = new ProgresionesEntrenamiento();
        when(service.buscarEntidades()).thenReturn(Collections.singletonList(progresion));

        // When & Then
        mockMvc.perform(get("/progresionesEntrenamientos"))
                .andExpect(status().isOk())
                .andExpect(view().name("progresiones"))
                .andExpect(model().attributeExists("progresiones"));
    }



    @Test
    void testGuardarProgresion_Error() throws Exception {
        // Given
        ProgresionesEntrenamiento progresion = new ProgresionesEntrenamiento();
        doThrow(new RuntimeException("Error")).when(service).guardar(any(ProgresionesEntrenamiento.class));

        // When & Then
        mockMvc.perform(post("/progresionesEntrenamientos/guardar")
                        .param("nombre", "Progresion Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("progresiones"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testEditarProgresion_Success() throws Exception {
        // Given
        ProgresionesEntrenamiento progresion = new ProgresionesEntrenamiento();
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.of(progresion));

        // When & Then
        mockMvc.perform(get("/progresionesEntrenamientos/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editarProgresion"))
                .andExpect(model().attributeExists("progresion"));
    }

    @Test
    void testEditarProgresion_NotFound() throws Exception {
        // Given
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/progresionesEntrenamientos/editar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/progresionesEntrenamientos"));
    }

    @Test
    void testEliminarProgresion_Success() throws Exception {
        // Given
        doNothing().when(service).eliminarPorId(anyInt());

        // When & Then
        mockMvc.perform(get("/progresionesEntrenamientos/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/progresionesEntrenamientos"));
    }
}
