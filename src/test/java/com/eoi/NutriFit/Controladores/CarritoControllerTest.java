package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Producto;
import com.eoi.NutriFit.Servicios.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.mock.web.MockCookie;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.30"));

    @BeforeAll
    static void setUp() {
        mysqlContainer.start();
    }

    @Test
    @WithMockUser
    void testMostrarCarrito() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/carrito"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("carrito"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("carrito"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("carritoTotal"));
    }

    @Test
    @WithMockUser
    void testAgregarProductoAlCarrito() throws Exception {
        Integer idProducto = 1;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setPrecio(10.0);

        when(productoService.encuentraPorId(idProducto)).thenReturn(Optional.of(producto));

        mockMvc.perform(MockMvcRequestBuilders.post("/carrito/agregar/{idProducto}", idProducto)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/carrito"));
    }

    @Test
    @WithMockUser
    void testEliminarProductoDelCarrito() throws Exception {
        Integer idProducto = 1;
        Producto producto = new Producto();
        producto.setId(idProducto);
        producto.setPrecio(10.0);
        List<Producto> carrito = new ArrayList<>();
        carrito.add(producto);
        String carritoJson = new ObjectMapper().writeValueAsString(carrito);

        mockMvc.perform(MockMvcRequestBuilders.post("/carrito/eliminar/{idProducto}", idProducto)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .cookie(new MockCookie("carrito", carritoJson)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/carrito"));
    }
}
