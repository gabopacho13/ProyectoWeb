package co.edu.javeriana.jpa_example2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.jpa_example2.dto.CiudadRutasOrigenDTO;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.RutaService;

@RestController
@RequestMapping("/ciudad/origen")
public class CiudadRutasOrigenController {
    
    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private RutaService rutaService;

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{ciudadId}")
    public CiudadRutasOrigenDTO getCiudadRutasOrigen(@PathVariable Long ciudadId) {
        return ciudadService.getCiudadRutasOrigen(ciudadId).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/rutas")
    public List<RutaDTO> getAllRutas() {
        return rutaService.listarRutas();
    }

    @Secured({Role.Code.ADMIN})
    @PutMapping("/actualizar")
    public CiudadRutasOrigenDTO updateCiudadRutasOrigen(@RequestBody CiudadRutasOrigenDTO ciudadRutasOrigenDTO) {
        return ciudadService.updateCiudadRutasOrigen(ciudadRutasOrigenDTO);
    }
}
