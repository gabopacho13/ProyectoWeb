package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.service.ServiciosCompradosService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.model.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/caravana/servicios")
public class ServiciosCompradosController {
    
    @Autowired
    ServiciosCompradosService serviciosCompradosService;
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<ServiciosCompradosDTO> listarServiciosComprados() {
        log.info("Lista de Servicios Comprados");
        return serviciosCompradosService.listarServiciosComprados();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{idCaravana}")
    public List<ServiciosCompradosDTO> listarServiciosComprados(@PathVariable("idCaravana") Long idCaravana) {
        return serviciosCompradosService.buscarServiciosCompradosPorCaravana(idCaravana);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PostMapping
    public ServiciosCompradosDTO crearServiciosComprados(@RequestBody ServiciosCompradosDTO serviciosCompradosDTO) {
        return serviciosCompradosService.guardarServicioscomprados(serviciosCompradosDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PutMapping
    public ServiciosCompradosDTO editarServiciosComprados(@RequestBody ServiciosCompradosDTO serviciosCompradosDTO) {
        return serviciosCompradosService.actualizarServiciosComprados(serviciosCompradosDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @DeleteMapping("/{idServiciosComprados}")
    public void borrarServiciosComprados(@PathVariable("idServiciosComprados") Long id) {
        serviciosCompradosService.borrarServiciosComprados(id);
    }
}
