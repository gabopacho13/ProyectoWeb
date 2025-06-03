package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.ServicioService;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<ServicioDTO> listarServicios() {
        log.info("Lista de Servicios");
        return servicioService.listarServicios();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{id}")
    public ServicioDTO obtenerServicio(@PathVariable Long id) {
        return servicioService.buscarServicio(id).orElseThrow();
    }

    @Secured({Role.Code.ADMIN})
    @PostMapping("/crear")
    public ServicioDTO crearServicio(@RequestBody ServicioDTO servicioDTO) {
        return servicioService.guardarServicio(servicioDTO);
    }

    @Secured({Role.Code.ADMIN})
    @PutMapping("/actualizar/{id}")
    public ServicioDTO actualizarServicio(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO) {
        servicioDTO.setId(id);
        return servicioService.actualizarServicio(servicioDTO);
    }
    
    @Secured({Role.Code.ADMIN})
    @DeleteMapping("/eliminar/{id}")
    public void eliminarServicio(@PathVariable Long id) {
        servicioService.borrarServicio(id);
    }
}
