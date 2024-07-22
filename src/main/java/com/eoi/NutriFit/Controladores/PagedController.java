
package com.eoi.NutriFit.Controladores;

import com.eoi.NutriFit.Entidades.Usuario;

import com.eoi.NutriFit.Repositorios.UsuarioPagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PagedController {

    private final UsuarioPagingAndSortingRepository usuarioPagingAndSortingRepository;

    public PagedController(UsuarioPagingAndSortingRepository usuarioPagingAndSortingRepository) {
        this.usuarioPagingAndSortingRepository = usuarioPagingAndSortingRepository;
    }

    @GetMapping("/listaUsuarios")
    public String listBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        PageRequest pageRequest = PageRequest.of(currentPage-1, pageSize);

        Page<Usuario> pagina = usuarioPagingAndSortingRepository.findAll(pageRequest);

        model.addAttribute("pagina", pagina);

        int totalPages = pagina.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "listaUsuarios.html";
    }

}
