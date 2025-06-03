package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.service.InventarioCaravanaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.model.Role;

import java.util.List;


@RestController
@RequestMapping("/Caravana/Inventario")
public class InventarioCaravanaController {
    
    @Autowired
    private InventarioCaravanaService inventarioCaravanaService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idCaravana}")
    public List<InventarioCaravanaDTO> getInventariosCaravana(@PathVariable Long idCaravana) {
        return inventarioCaravanaService.buscarInventarioCaravanaPorId(idCaravana);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<InventarioCaravanaDTO> listarInventariosCaravana() {
        return inventarioCaravanaService.listarInventarioCaravanas();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping
    public InventarioCaravanaDTO actualizarInventarioCaravana(@RequestBody InventarioCaravanaDTO inventarioCaravanaDTO) {
        return inventarioCaravanaService.actualizarInventarioCaravana(inventarioCaravanaDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN}) 
    @PostMapping
    public InventarioCaravanaDTO crearInventarioCaravana(@RequestBody InventarioCaravanaDTO inventarioCaravanaDTO) {
        return inventarioCaravanaService.guardarInventarioCaravana(inventarioCaravanaDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @DeleteMapping("/{idInventarioCaravana}")
    public void borrarInventarioCaravana(@PathVariable("idInventarioCaravana") Long id) {
        inventarioCaravanaService.borrarInventarioCaravana(id);
    }
}
