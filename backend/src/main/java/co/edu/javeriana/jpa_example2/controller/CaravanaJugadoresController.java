package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.dto.CaravanaJugadoresDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/caravana/jugadores")
public class CaravanaJugadoresController {

    @Autowired
    CaravanaService caravanaService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE})
    @GetMapping("/{idCaravana}")
    public CaravanaJugadoresDTO listarJugadores(@PathVariable("idCaravana") Long idCaravana) {
        return caravanaService.listarJugadoresPorCaravana(idCaravana);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE})
    @PutMapping("/{idCaravana}")
    public CaravanaJugadoresDTO editarJugadores(@PathVariable("idCaravana") Long idCaravana, @RequestBody CaravanaJugadoresDTO caravanaJugadoresDTO) {
        List<Long> jugadores = caravanaJugadoresDTO.getJugadoresIds();
        return caravanaService.editarJugadoresPorCaravana(idCaravana, jugadores);
    }
}
