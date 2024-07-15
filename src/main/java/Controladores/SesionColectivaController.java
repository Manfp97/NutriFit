//package Controladores;
//
//
//import com.eoi.NutriFit.Entidades.SesionColectiva;
//import com.eoi.NutriFit.Servicios.SesionColectivaServi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/sesionColectiva")
//public class SesionColectivaController {
//
//    @Autowired
//    private SesionColectivaServi service;
//
//    @GetMapping
//    public String getAll(Model model) {
//        List<SesionColectiva> listaDetalles = service.buscarEntidades();
//        model.addAttribute("detalleSesionColectivas", listaDetalles);
//        return "detalleSesionColectivas";
//
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SesionColectiva> getById(@PathVariable Integer id) {
//        Optional<SesionColectiva> detalleSesionColectiva = service.encuentraPorId(id);
//        if (detalleSesionColectiva.isPresent()) {
//            return ResponseEntity.ok(detalleSesionColectiva.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public SesionColectiva create(@RequestBody SesionColectiva detalleSesionColectiva) throws Exception {
//        List<SesionColectiva> SesionColectiva = List.of();
//        return service.guardar(SesionColectiva);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity update(@PathVariable Integer id, @RequestBody SesionColectiva detalleSesionColectiva) throws Exception {
//        Optional<SesionColectiva> existingDetalleSesionColectiva = service.encuentraPorId(id);
//        if (existingDetalleSesionColectiva.isPresent()) {
//            detalleSesionColectiva.setId(id);
//            return (ResponseEntity) ResponseEntity.ok();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Integer id) {
//        if (service.encuentraPorId(id).isPresent()) {
//            service.eliminarPorId(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}