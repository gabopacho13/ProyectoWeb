package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import co.edu.javeriana.jpa_example2.service.PartidaService;
import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.model.Role;

@RestController
@RequestMapping("/partida")
public class PartidaController {
    
    @Autowired
    private PartidaService partidaService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Secured({Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<PartidaDTO> listarPartidas() {
        log.info("Lista de Partidas");
        return partidaService.listarPartidas();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idPartida}")
    public PartidaDTO listarPartida(@PathVariable("idPartida") Long id) {
        return partidaService.buscarPartida(id).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PostMapping
    public PartidaDTO crearPartida(@RequestBody PartidaDTO partidaDTO) {
        return partidaService.guardarPartida(partidaDTO);
    }
    
    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @PutMapping
    public PartidaDTO editarPartida(@RequestBody PartidaDTO partidaDTO) {
        return partidaService.actualizarPartida(partidaDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.ADMIN})
    @DeleteMapping("/{idPartida}")
    public void borrarPartida(@PathVariable("idPartida") Long id) {
        partidaService.borrarPartida(id);
    }
}
