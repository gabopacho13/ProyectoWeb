package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        List<Ciudad> ciudades = ciudadService.listarCiudades();
        ModelAndView modelAndView = new ModelAndView("ciudad-lista");
        modelAndView.addObject("listaCiudades",ciudades);
        return modelAndView;
    }

    @GetMapping("/view/{idCiudad}")
    public ModelAndView listarCiudad(@PathVariable("idCiudad") Long id){
        Ciudad ciudad = ciudadService.buscarCiudad(id);
        ModelAndView modelAndView = new ModelAndView("ciudad-view");
        modelAndView.addObject("ciudad",ciudad);
        return modelAndView;
    }
}
