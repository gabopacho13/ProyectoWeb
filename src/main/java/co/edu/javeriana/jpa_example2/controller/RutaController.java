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
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.service.RutaService;

@Controller
@RequestMapping("/ruta")
public class RutaController {
    
    @Autowired
    private RutaService rutaService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public ModelAndView listarRutas(){
        log.info("Lista de Rutas");
        List<RutaDTO> rutas = rutaService.listarRutas();
        ModelAndView modelAndView = new ModelAndView("ruta-lista");
        modelAndView.addObject("listaRutas", rutas);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView listarRuta(@PathVariable Long id){
        RutaDTO ruta = rutaService.buscarRutas(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ruta-view");
        modelAndView.addObject("ruta", ruta);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView formularioCrearRuta() {
        ModelAndView modelAndView = new ModelAndView("ruta-edit"); 
        modelAndView.addObject("ruta", new RutaDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formularioEditarRuta(@PathVariable Long id) {
        RutaDTO rutaDto = rutaService.buscarRutas(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ruta-edit"); 
        modelAndView.addObject("ruta", rutaDto);
        return modelAndView;
    }
    
    @PostMapping("/save")
    public RedirectView guardarRuta(@ModelAttribute RutaDTO rutaDto){
        rutaService.guardarRuta(rutaDto);
        return new RedirectView("/ruta/lista");
    }

    @GetMapping("/delete/{id}")
    public RedirectView borrarRuta(@PathVariable Long id) {
        rutaService.borrarRuta(id);
        return new RedirectView("/ruta/lista");
    }
}
