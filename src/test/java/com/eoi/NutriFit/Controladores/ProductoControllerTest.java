package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Repositorios.ProductoRepo;
import com.eoi.NutriFit.Servicios.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private ProductoRepo productoRepo;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
                .build();
    }


    @Test
    void testListAll_WithCategoria_Success() throws Exception {
        Page<Producto> page = new PageImpl<>(Collections.singletonList(new Producto()));
        when(productoRepo.findByCategoria(anyString(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/producto").param("categoria", "proteina"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("pagina", "pageNumbers", "productos", "categoria"));
    }




    @Test
    void testListAllEditable_Success() throws Exception {
        Page<Producto> page = new PageImpl<>(Collections.singletonList(new Producto()));
        when(productoRepo.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/producto/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("listaproductoseditable"))
                .andExpect(model().attributeExists("productosPage", "pageNumbers"));
    }

    @Test
    void testGetById_ExistingProduct_Success() throws Exception {
        when(productoService.encuentraPorId(anyInt())).thenReturn(Optional.of(new Producto()));

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("detalleproducto"))
                .andExpect(model().attributeExists("producto"));
    }

    @Test
    void testGetById_ProductNotFound_RedirectsTo404() throws Exception {
        when(productoService.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/producto/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));
    }

    @Test
    void testMostrarFormulario_Success() throws Exception {
        mockMvc.perform(get("/producto/nuevo")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("crearproducto"))
                .andExpect(model().attributeExists("producto"));
    }

    @Test
    void testCrear_ValidProduct_Success() throws Exception {
        when(productoService.guardar(any(Producto.class))).thenReturn(new Producto());

        mockMvc.perform(post("/producto/nuevo")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .param("nombre", "New Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producto/nuevo"))
                .andExpect(flash().attribute("mensaje", "Producto creado con éxito"));
    }

    @Test
    void testUpdate_ExistingProduct_Success() throws Exception {
        when(productoService.encuentraPorId(anyInt())).thenReturn(Optional.of(new Producto()));
        when(productoService.guardar(any(Producto.class))).thenReturn(new Producto());

        mockMvc.perform(post("/producto/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .param("nombre", "Updated Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producto"))
                .andExpect(flash().attribute("mensaje", "Producto actualizado con éxito"));
    }

    @Test
    void testUpdate_ProductNotFound_RedirectsToProductPage() throws Exception {
        when(productoService.encuentraPorId(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(post("/producto/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .param("nombre", "Updated Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producto"))
                .andExpect(flash().attribute("mensaje", "Producto no encontrado"));
    }

    @Test
    void testDelete_ExistingProduct_Success() throws Exception {
        doNothing().when(productoService).eliminarPorId(anyInt());

        mockMvc.perform(post("/producto/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producto"));
    }

    @Test
    void testDelete_ProductNotFound_RedirectsTo404() throws Exception {
        doThrow(new EntityNotFoundException()).when(productoService).eliminarPorId(anyInt());

        mockMvc.perform(post("/producto/delete/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/404"));
    }
}
