package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.service.ServiciosCompradosService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/caravana/servicios")
public class ServiciosCompradosController {
    
    @Autowired
    ServiciosCompradosService serviciosCompradosService;
    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public List<ServiciosCompradosDTO> listarServiciosComprados() {
        log.info("Lista de Servicios Comprados");
        return serviciosCompradosService.listarServiciosComprados();
    }

    @GetMapping("/{idCaravana}")
    public List<ServiciosCompradosDTO> listarServiciosComprados(@PathVariable("idCaravana") Long idCaravana) {
        return serviciosCompradosService.buscarServiciosCompradosPorCaravana(idCaravana);
    }

    @PostMapping
    public ServiciosCompradosDTO crearServiciosComprados(@RequestBody ServiciosCompradosDTO serviciosCompradosDTO) {
        return serviciosCompradosService.guardarServicioscomprados(serviciosCompradosDTO);
    }

    @PutMapping
    public ServiciosCompradosDTO editarServiciosComprados(@RequestBody ServiciosCompradosDTO serviciosCompradosDTO) {
        return serviciosCompradosService.actualizarServiciosComprados(serviciosCompradosDTO);
    }

    @DeleteMapping("/{idServiciosComprados}")
    public void borrarServiciosComprados(@PathVariable("idServiciosComprados") Long id) {
        serviciosCompradosService.borrarServiciosComprados(id);
    }
}
