package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.dto.CaravanaDTO;
import co.edu.javeriana.jpa_example2.model.Role;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/caravana")
public class CaravanaController {

    @Autowired
    private CaravanaService caravanaService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Secured(Role.Code.ADMIN)
    @GetMapping("/lista")
    public List<CaravanaDTO> listarCaravanas() {
        log.info("Lista de Caravanas");
        return caravanaService.listarCaravanas();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idCaravana}")
    public CaravanaDTO listarCaravana(@PathVariable("idCaravana") Long id) {
        return caravanaService.buscarCaravana(id).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PostMapping
    public CaravanaDTO crearCaravana(@RequestBody CaravanaDTO caravanaDTO) {
        return caravanaService.guardarCaravana(caravanaDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PutMapping
    public CaravanaDTO editarCaravana(@RequestBody CaravanaDTO caravanaDTO) {
        return caravanaService.actualizarCaravana(caravanaDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @DeleteMapping("/{idCaravana}")
    public void borrarCaravana(@PathVariable("idCaravana") Long id) {
        caravanaService.borrarCaravana(id);
    }
}
