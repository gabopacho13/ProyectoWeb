package co.edu.javeriana.jpa_example2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.RutaService;

@RestController
@RequestMapping("/ruta")
public class RutaController {
    
    @Autowired
    private RutaService rutaService;

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("/{id}")
    public RutaDTO obtenerRuta(@PathVariable Long id) {
        return rutaService.buscarRutas(id).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @GetMapping("conexiones/{id}")
    public List<CiudadDTO> obtenerConexiones(@PathVariable Long id) {
        return rutaService.obtenerConexiones(id).orElseThrow();
    } 

    @Secured({Role.Code.ADMIN})
    @PostMapping("/crear")
    public RutaDTO crearRuta(@RequestBody RutaDTO rutaDto) {
        rutaDto.setId(null);
        return rutaService.guardarRuta(rutaDto);
    }

    @Secured({Role.Code.ADMIN})
    @PutMapping("/actualizar/{id}")
    public RutaDTO actualizarRuta(@PathVariable Long id, @RequestBody RutaDTO rutaDTO) {
        rutaDTO.setId(id);
        return rutaService.actualizarRuta(rutaDTO);
    }

    @Secured({Role.Code.ADMIN})
    @DeleteMapping("/eliminar/{id}")
    public void borrarRuta(@PathVariable Long id) {
        rutaService.borrarRuta(id);
    }
}
