package co.edu.javeriana.jpa_example2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Role;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import co.edu.javeriana.jpa_example2.service.ServicioCiudadService;

@RestController
@RequestMapping("/ciudad/servicios")
public class ServicioCiudadController {
    
    @Autowired
    ServicioCiudadService servicioCiudadService;

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<ServicioCiudadDTO> listarServicioCiudades() {
        return servicioCiudadService.listarServicioCiudades();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{idCiudad}")
    public List<ServicioCiudadDTO> getServiciosCiudad(@PathVariable("idCiudad") Long idCiudad) {
        return servicioCiudadService.buscarServicioCiudadPorCiudad(idCiudad);
    }

    @Secured({Role.Code.ADMIN})
    @PostMapping
    public ServicioCiudadDTO crearServicioCiudad(@RequestBody ServicioCiudadDTO servicioCiudadDTO) {
        return servicioCiudadService.guardarServicioCiudad(servicioCiudadDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PutMapping
    public ServicioCiudadDTO editarServicioCiudad(@RequestBody ServicioCiudadDTO servicioCiudadDTO) {
        return servicioCiudadService.actualizarServicioCiudad(servicioCiudadDTO);
    }

    @Secured({Role.Code.ADMIN})
    @DeleteMapping("/{idServicioCiudad}")
    public void borrarServicioCiudad(@PathVariable("idServicioCiudad") Long id) {
        servicioCiudadService.borrarServicioCiudad(id);
    }
}
