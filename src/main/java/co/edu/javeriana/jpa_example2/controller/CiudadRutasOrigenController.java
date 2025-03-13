package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import co.edu.javeriana.jpa_example2.dto.CiudadRutasOrigenDTO;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.RutaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/ciudad/origen")
public class CiudadRutasOrigenController {
    @Autowired
    private CiudadService ciudadService;
    @Autowired
    private RutaService rutaService;

    @GetMapping("edit/{ciudadId}")
    public ModelAndView editCiudadOrigenForm(@PathVariable Long ciudadId) {
        CiudadRutasOrigenDTO ciudadRutasOrigenDTO = ciudadService.getCiudadRutasOrigen(ciudadId).orElseThrow();
        List<RutaDTO> allRutas = rutaService.recuperarRutas();

        ModelAndView modelAndView = new ModelAndView("ciudad-origen-edit");
        modelAndView.addObject("ciudadOrigen",ciudadRutasOrigenDTO);
        modelAndView.addObject("allRutas",allRutas);
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView saveCiudadOrigenForm(@ModelAttribute CiudadRutasOrigenDTO ciudadRutasOrigenDTO) {
        ciudadService.updateCiudadRutasOrigen(ciudadRutasOrigenDTO);
        return new RedirectView("/ciudad/lista");
    }
    
}
