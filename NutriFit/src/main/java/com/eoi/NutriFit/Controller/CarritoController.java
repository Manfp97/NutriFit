package com.eoi.NutriFit.Controller;

import com.eoi.NutriFit.Entidades.Carrito;
import com.eoi.NutriFit.Servicios.CarritoServi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/carrito")
@SessionAttributes("carrito")
public class CarritoController {

    @Autowired
    private CarritoServi carritoServi;

    @ModelAttribute("carrito")
    public Carrito crearCarrito() {
        return new Carrito();
    }

    @GetMapping
    public String listarCarritos(Model model) {
        List<Carrito> listaCarrito = carritoServi.buscarEntidades();
        model.addAttribute("carritos", listaCarrito);
        return "carrito";
    }

    @GetMapping("/{id}")
    public String mostrarCarrito(@PathVariable Integer id, Model model) {
        Optional<Carrito> carritoOpt = carritoServi.encuentraPorId(id);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            model.addAttribute("carrito", carrito);
            model.addAttribute("precioTotal", carrito.getPrecioTotal());
            return "carrito-detalles";
        } else {
            return "redirect:/carrito";
        }
    }

    @PostMapping
    public String crearCarrito(@RequestBody Carrito carrito) throws Exception {
        carritoServi.guardar(carrito);
        return "redirect:/carrito";
    }


    @DeleteMapping("/carrito/{id}")
    public String eliminarCarrito(@PathVariable Integer id, Model model) {
        carritoServi.eliminarPorId(id);
        return "redirect:/carrito";
    }

}
