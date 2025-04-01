package co.edu.javeriana.jpa_example2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.service.RutaService;

@RestController
@RequestMapping("/ruta")
public class RutaController {
    
    @Autowired
    private RutaService rutaService;

    @GetMapping("/lista")
    public List<RutaDTO> listarRutas() {
        return rutaService.listarRutas();
    }

    @GetMapping("/{id}")
    public RutaDTO obtenerRuta(@PathVariable Long id) {
        return rutaService.buscarRutas(id).orElseThrow();
    }

    @PostMapping("/crear")
    public RutaDTO crearRuta(@RequestBody RutaDTO rutaDto) {
        rutaDto.setId(null);
        return rutaService.guardarRuta(rutaDto);
    }

    @PutMapping("/actualizar/{id}")
    public RutaDTO actualizarRuta(@PathVariable Long id, @RequestBody RutaDTO rutaDTO) {
        rutaDTO.setId(id);
        return rutaService.actualizarRuta(rutaDTO);
    }

    @DeleteMapping("/eliminar/{id}")
    public void borrarRuta(@PathVariable Long id) {
        rutaService.borrarRuta(id);
    }
}
