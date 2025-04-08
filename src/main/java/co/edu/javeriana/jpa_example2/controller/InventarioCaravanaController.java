package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.service.InventarioCaravanaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import java.util.List;


@RestController
@RequestMapping("/Caravana/Inventario")
public class InventarioCaravanaController {
    
    @Autowired
    private InventarioCaravanaService inventarioCaravanaService;

    @GetMapping("/{idCaravana}")
    public List<InventarioCaravanaDTO> getInventariosCaravana(@PathVariable Long idCaravana) {
        return inventarioCaravanaService.buscarInventarioCaravanaPorId(idCaravana);
    }

    @GetMapping("/lista")
    public List<InventarioCaravanaDTO> listarInventariosCaravana() {
        return inventarioCaravanaService.listarInventarioCaravanas();
    }

    @PutMapping
    public InventarioCaravanaDTO actualizarInventarioCaravana(@RequestBody InventarioCaravanaDTO inventarioCaravanaDTO) {
        return inventarioCaravanaService.actualizarInventarioCaravana(inventarioCaravanaDTO);
    }

    @PostMapping
    public InventarioCaravanaDTO crearInventarioCaravana(@RequestBody InventarioCaravanaDTO inventarioCaravanaDTO) {
        return inventarioCaravanaService.guardarInventarioCaravana(inventarioCaravanaDTO);
    }

    @DeleteMapping("/{idInventarioCaravana}")
    public void borrarInventarioCaravana(@PathVariable("idInventarioCaravana") Long id) {
        inventarioCaravanaService.borrarInventarioCaravana(id);
    }
}
