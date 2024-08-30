package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Dieta;
import com.eoi.NutriFit.Repositorios.DietaRepo;
import com.eoi.NutriFit.Servicios.DietaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DietaControllerTest {

    @Mock
    private DietaService service;

    @Mock
    private DietaRepo dietaRepo;

    @Mock
    private Model model;

    @InjectMocks
    private DietaController dietaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dietaController).build();
    }

    @Test
    void testListAll_WithCategory_ReturnsDietasPage() throws Exception {
        Page<Dieta> dietasPage = new PageImpl<>(Arrays.asList(new Dieta(), new Dieta()));
        when(dietaRepo.findByCategoria(anyString(), any(Pageable.class))).thenReturn(dietasPage);

        mockMvc.perform(get("/dietaUsuario")
                        .param("page", "0")
                        .param("size", "9")
                        .param("categoria", "aumentodemasamuscular"))
                .andExpect(status().isOk())
                .andExpect(view().name("dieta"))
                .andExpect(model().attributeExists("pagina"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(model().attributeExists("dietas"))
                .andExpect(model().attributeExists("categoria"));

        verify(dietaRepo, times(1)).findByCategoria(eq("aumentodemasamuscular"), any(Pageable.class));
    }



    @Test
    void testListAll_NoDietasFound_ReturnsDietaNotFoundPage() throws Exception {
        when(dietaRepo.findByCategoria(anyString(), any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/dietaUsuario")
                        .param("categoria", "aumentodemasamuscular"))
                .andExpect(status().isOk())
                .andExpect(view().name("dietanotfound"));

        verify(dietaRepo, times(1)).findByCategoria(eq("aumentodemasamuscular"), any(Pageable.class));
    }

    @Test
    void testGetById_DietaFound_ReturnsDietaDetail() throws Exception {
        Dieta dieta = new Dieta();
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.of(dieta));

        mockMvc.perform(get("/dietaUsuario/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("detalledieta"))
                .andExpect(model().attributeExists("dieta"));

        verify(service, times(1)).encuentraPorId(1);
    }

    @Test
    void testGetById_DietaNotFound_RedirectsTo404() throws Exception {
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/dietaUsuario/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));

        verify(service, times(1)).encuentraPorId(1);
    }

    @Test
    void testUpdate_DietaFoundAndUpdated_ReturnsRedirectToDietaUsuario() throws Exception {
        Dieta dieta = new Dieta();
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.of(dieta));

        mockMvc.perform(post("/dietaUsuario/1")
                        .param("nombre", "Nueva Dieta")
                        .param("objetivos", "Objetivo Actualizado")
                        .param("categoria", "Nueva Categoria")
                        .param("descripcion", "Descripción Actualizada"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dietaUsuario"));

        verify(service, times(1)).encuentraPorId(1);
        verify(service, times(1)).guardar(any(Dieta.class));
    }

    @Test
    void testUpdate_DietaNotFound_ReturnsRedirectToDietaUsuario() throws Exception {
        when(service.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(post("/dietaUsuario/1")
                        .param("nombre", "Nueva Dieta")
                        .param("objetivos", "Objetivo Actualizado")
                        .param("categoria", "Nueva Categoria")
                        .param("descripcion", "Descripción Actualizada"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dietaUsuario"));

        verify(service, times(1)).encuentraPorId(1);
        verify(service, never()).guardar(any(Dieta.class));
    }

    @Test
    void testDelete_DietaDeleted_ReturnsRedirectToDietaUsuario() throws Exception {
        doNothing().when(service).eliminarPorId(anyInt());

        mockMvc.perform(post("/dietaUsuario/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dietaUsuario"));

        verify(service, times(1)).eliminarPorId(1);
    }

    @Test
    void testDelete_DietaNotFound_ReturnsRedirectTo404() throws Exception {
        doThrow(EntityNotFoundException.class).when(service).eliminarPorId(anyInt());

        mockMvc.perform(post("/dietaUsuario/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));

        verify(service, times(1)).eliminarPorId(1);
    }

    @Test
    void testMostrarFormulario_ReturnsCrearDietaForm() throws Exception {
        mockMvc.perform(get("/dietaUsuario/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("creardieta"))
                .andExpect(model().attributeExists("dieta"));
    }

    @Test
    void testCrear_DietaCreated_ReturnsRedirectToNuevo() throws Exception {
        Dieta dieta = new Dieta();
        when(service.guardar(any(Dieta.class))).thenReturn(dieta);

        mockMvc.perform(post("/dietaUsuario/nuevo")
                        .param("nombre", "Nueva Dieta")
                        .param("objetivos", "Objetivo")
                        .param("categoria", "Categoria")
                        .param("descripcion", "Descripción"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dietaUsuario/nuevo"));

        verify(service, times(1)).guardar(any(Dieta.class));
    }


    @Test
    void testCrear_ErrorCreatingDieta_ReturnsRedirectTo404() throws Exception {
        doThrow(RuntimeException.class).when(service).guardar(any(Dieta.class));

        mockMvc.perform(post("/dietaUsuario/nuevo")
                        .param("nombre", "Nueva Dieta")
                        .param("objetivos", "Objetivo")
                        .param("categoria", "Categoria")
                        .param("descripcion", "Descripción"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));

        verify(service, times(1)).guardar(any(Dieta.class));
    }
}
