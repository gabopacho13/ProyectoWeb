package co.edu.javeriana.jpa_example2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.RutaService;
import co.edu.javeriana.jpa_example2.dto.CiudadRutasDestinoDTO;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Role;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ciudad/destino")
public class CiudadRutasDestinoController {
    @Autowired
    private CiudadService ciudadService;
    
    @Autowired
    private RutaService rutaService;

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{ciudadId}")
    public CiudadRutasDestinoDTO getCiudadRutasDestino(@PathVariable Long ciudadId) {
        return ciudadService.getCiudadRutasDestino(ciudadId).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/rutas")
    public List<RutaDTO> getAllRutas() {
        return rutaService.listarRutas();
    }
    
    @Secured({Role.Code.ADMIN})
    @PutMapping("/actualizar")
    public CiudadRutasDestinoDTO updateCiudadRutasDestino(@RequestBody CiudadRutasDestinoDTO ciudadRutasOrigenDTO) {
        return ciudadService.updateCiudadRutasDestino(ciudadRutasOrigenDTO);
    }
}
