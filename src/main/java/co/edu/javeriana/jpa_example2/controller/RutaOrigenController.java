package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.RutaOrigenDTO;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.RutaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/ruta/origen")
public class RutaOrigenController {
    @Autowired
    private RutaService rutaService;
    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/edit/{ciudadId}")
    public ModelAndView editOrigenRutaForm(@RequestParam Long ciudadId) {
        RutaOrigenDTO rutaOrigenDTO = ciudadService.getRutaOrigen(ciudadId).orElseThrow();
        List<CiudadDTO> allCiudades = ciudadService.listarCiudades();

        ModelAndView modelAndView = new ModelAndView("ruta-otigen-edit");
        modelAndView.addObject("ciudadOrigen", rutaOrigenDTO);
        modelAndView.addObject("allCiudades", allCiudades);
        return modelAndView;
    }
    
}
