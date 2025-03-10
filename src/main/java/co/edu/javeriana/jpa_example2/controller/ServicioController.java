package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.service.ServicioService;

@Controller
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public ModelAndView listarServicios() {
        log.info("Lista de Servicios");
        List<ServicioDTO> servicios = servicioService.listarServicios();
        ModelAndView modelAndView = new ModelAndView("servicio-lista");
        modelAndView.addObject("listaServicios", servicios);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView verServicio(@PathVariable Long id) {
        ServicioDTO servicio = servicioService.buscarServicio(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("servicio-view");
        modelAndView.addObject("servicio", servicio);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView formularioCrearServicio() {
        ModelAndView modelAndView = new ModelAndView("servicio-edit");
        modelAndView.addObject("servicio", new ServicioDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formularioEditarServicio(@PathVariable Long id) {
        ServicioDTO servicioDTO = servicioService.buscarServicio(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("servicio-edit");
        modelAndView.addObject("servicio", servicioDTO);
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView guardarServicio(@ModelAttribute ServicioDTO servicioDTO) {
        servicioService.guardarServicio(servicioDTO);
        return new RedirectView("/servicio/lista");
    }

    @GetMapping("/delete/{id}")
    public RedirectView borrarServicio(@PathVariable Long id) {
        servicioService.borrarServicio(id);
        return new RedirectView("/servicio/lista");
    }
}
