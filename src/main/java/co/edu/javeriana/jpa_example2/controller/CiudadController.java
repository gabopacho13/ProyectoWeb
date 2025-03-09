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

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.service.CiudadService;

@Controller
@RequestMapping("/ciudad")
public class CiudadController {
    @Autowired
    private CiudadService ciudadService;

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @GetMapping("/lista")
    public ModelAndView listarCiudades(){
        log.info("Lista de Ciudades");
        List<CiudadDTO> ciudades = ciudadService.listarCiudades();
        ModelAndView modelAndView = new ModelAndView("ciudad-lista");
        modelAndView.addObject("listaCiudades",ciudades);
        return modelAndView;
    }

    @GetMapping("/view/{idCiudad}")
    public ModelAndView listarCiudad(@PathVariable("idCiudad") Long id){
        CiudadDTO ciudad = ciudadService.buscarCiudad(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ciudad-view");
        modelAndView.addObject("ciudad",ciudad);
        return modelAndView;
    }

     @GetMapping("/create")
    public ModelAndView formularioCrearCiudad() {
        ModelAndView modelAndView = new ModelAndView("ciudad-edit"); 
        modelAndView.addObject("ciudad", new CiudadDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formularioEditarCiudad(@PathVariable Long id) {
        CiudadDTO ciudadDTO = ciudadService.buscarCiudad(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("ciudad-edit"); 
        modelAndView.addObject("ciudad",ciudadDTO);
        return modelAndView;
    }
    
    @PostMapping("/save")
    public RedirectView guardarCiudad(@ModelAttribute CiudadDTO ciudadDTO){
        ciudadService.guardarCiudad(ciudadDTO);
        return new RedirectView("/ciudad/lista");
    }

    @GetMapping("/delete/{id}")
    public RedirectView borrarCiudad(@PathVariable Long id) {
        ciudadService.borrarCiudad(id);
        return new RedirectView("/ciudad/lista");
    }
}
