package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Entrenamiento;
import com.eoi.NutriFit.Repositorios.EntrenamientoRepo;
import com.eoi.NutriFit.Servicios.EntrenamientoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EntrenamientoControllerTest {

    @Mock
    private EntrenamientoService service;

    @Mock
    private EntrenamientoRepo entrenamientoRepo;

    @InjectMocks
    private EntrenamientoController entrenamientoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(entrenamientoController)
                .setViewResolvers(new InternalResourceViewResolver())
                .build();
    }



    @Test
    @WithMockUser(roles = {"ADMIN", "EMPLEADO"})
    void testListAllEditable_ReturnsEditablePage() throws Exception {
        Page<Entrenamiento> entrenamientoPage = new PageImpl<>(Arrays.asList(new Entrenamiento(), new Entrenamiento()));
        when(entrenamientoRepo.findAll(any(Pageable.class))).thenReturn(entrenamientoPage);

        mockMvc.perform(get("/entrenamiento/list")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("listaentrenamientoseditable"))
                .andExpect(model().attributeExists("entrenamientoPage"))
                .andExpect(model().attributeExists("pageNumbers"));

        verify(entrenamientoRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetById_EntrenamientoFound_ReturnsDetailPage() throws Exception {
        Entrenamiento entrenamiento = new Entrenamiento();
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.of(entrenamiento));

        mockMvc.perform(get("/entrenamiento/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("detalleentrenamiento"))
                .andExpect(model().attributeExists("entrenamiento"));

        verify(service, times(1)).encuentraPorId(1);
    }

    @Test
    void testGetById_EntrenamientoNotFound_RedirectsTo404() throws Exception {
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/entrenamiento/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));

        verify(service, times(1)).encuentraPorId(1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EMPLEADO"})
    void testUpdate_EntrenamientoFoundAndUpdated_RedirectsToEntrenamiento() throws Exception {
        Entrenamiento entrenamiento = new Entrenamiento();
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.of(entrenamiento));

        mockMvc.perform(post("/entrenamiento/1")
                        .param("nombre", "Entrenamiento Nuevo")
                        .param("objetivos", "Objetivos Actualizados")
                        .param("categoria", "Categoria Nueva"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrenamiento"));

        verify(service, times(1)).encuentraPorId(1);
        verify(service, times(1)).guardar(any(Entrenamiento.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EMPLEADO"})
    void testUpdate_EntrenamientoNotFound_RedirectsToEntrenamiento() throws Exception {
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(post("/entrenamiento/1")
                        .param("nombre", "Entrenamiento Nuevo")
                        .param("objetivos", "Objetivos Actualizados")
                        .param("categoria", "Categoria Nueva"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrenamiento"));

        verify(service, times(1)).encuentraPorId(1);
        verify(service, never()).guardar(any(Entrenamiento.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDelete_EntrenamientoDeleted_RedirectsToEntrenamiento() throws Exception {
        doNothing().when(service).eliminarPorId(anyInt());

        mockMvc.perform(post("/entrenamiento/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrenamiento"));

        verify(service, times(1)).eliminarPorId(1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDelete_EntrenamientoNotFound_RedirectsTo404() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).eliminarPorId(anyInt());

        mockMvc.perform(post("/entrenamiento/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));

        verify(service, times(1)).eliminarPorId(1);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EMPLEADO"})
    void testMostrarFormulario_ReturnsCrearEntrenamientoForm() throws Exception {
        mockMvc.perform(get("/entrenamiento/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("crearentrenamiento"))
                .andExpect(model().attributeExists("entrenamiento"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "EMPLEADO"})
    void testCrear_EntrenamientoCreated_RedirectsToNuevo() throws Exception {
        mockMvc.perform(post("/entrenamiento/nuevo")
                        .param("nombre", "Nuevo Entrenamiento")
                        .param("objetivos", "Objetivos del Nuevo Entrenamiento")
                        .param("categoria", "nuevaCategoria"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entrenamiento/nuevo"));

        verify(service, times(1)).guardar(any(Entrenamiento.class));
    }
}
