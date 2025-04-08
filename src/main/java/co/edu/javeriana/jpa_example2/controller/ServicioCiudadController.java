package co.edu.javeriana.jpa_example2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
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

    @GetMapping("/lista")
    public List<ServicioCiudadDTO> listarServicioCiudades() {
        return servicioCiudadService.listarServicioCiudades();
    }

    @GetMapping("/{idCiudad}")
    public List<ServicioCiudadDTO> getServiciosCiudad(@PathVariable("idCiudad") Long idCiudad) {
        return servicioCiudadService.buscarServicioCiudadPorCiudad(idCiudad);
    }

    @PostMapping
    public ServicioCiudadDTO crearServicioCiudad(@RequestBody ServicioCiudadDTO servicioCiudadDTO) {
        return servicioCiudadService.guardarServicioCiudad(servicioCiudadDTO);
    }

    @PutMapping
    public ServicioCiudadDTO editarServicioCiudad(@RequestBody ServicioCiudadDTO servicioCiudadDTO) {
        return servicioCiudadService.actualizarServicioCiudad(servicioCiudadDTO);
    }

    @DeleteMapping("/{idServicioCiudad}")
    public void borrarServicioCiudad(@PathVariable("idServicioCiudad") Long id) {
        servicioCiudadService.borrarServicioCiudad(id);
    }
}
