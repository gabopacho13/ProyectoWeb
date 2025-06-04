package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import co.edu.javeriana.jpa_example2.dto.JugadorDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.JugadorService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jugador")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public List<JugadorDTO> listarJugadores() {
        return jugadorService.listarJugadores();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{id}")
    public JugadorDTO obtenerJugador(@PathVariable Long id) {
        return jugadorService.buscarJugador(id).orElseThrow();
    }

   @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/email/{email}") 
    public JugadorDTO obtenerJugadorPorEmail(@PathVariable String email) {
        return jugadorService.buscarJugadorPorEmail(email).orElseThrow();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
     @PostMapping
    public JugadorDTO crearJugador(@RequestBody JugadorDTO jugadorDTO) {
        return jugadorService.guardarJugador(jugadorDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping
    public JugadorDTO editarJugador(@RequestBody JugadorDTO jugadorDTO) {
        return jugadorService.actualizarJugador(jugadorDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @DeleteMapping("/{id}")
    public void borrarJugador(@PathVariable Long id) {
        jugadorService.borrarJugador(id);
    }
}
