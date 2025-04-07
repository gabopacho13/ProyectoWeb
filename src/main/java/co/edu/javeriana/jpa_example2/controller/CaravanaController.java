package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.dto.CaravanaDTO;
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

    @GetMapping("/lista")
    public List<CaravanaDTO> listarCaravanas() {
        log.info("Lista de Caravanas");
        return caravanaService.listarCaravanas();
    }

    @GetMapping("/{idCaravana}")
    public CaravanaDTO listarCaravana(@PathVariable("idCaravana") Long id) {
        return caravanaService.buscarCaravana(id).orElseThrow();
    }

    @PostMapping
    public CaravanaDTO crearCaravana(@RequestBody CaravanaDTO caravanaDTO) {
        return caravanaService.guardarCaravana(caravanaDTO);
    }

    @PutMapping
    public CaravanaDTO editarCaravana(@RequestBody CaravanaDTO caravanaDTO) {
        return caravanaService.actualizarCaravana(caravanaDTO);
    }

    @DeleteMapping("/{idCaravana}")
    public void borrarCaravana(@PathVariable("idCaravana") Long id) {
        caravanaService.borrarCaravana(id);
    }
}
