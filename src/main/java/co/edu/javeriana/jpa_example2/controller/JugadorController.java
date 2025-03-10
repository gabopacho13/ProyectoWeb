package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import co.edu.javeriana.jpa_example2.dto.JugadorDTO;
import co.edu.javeriana.jpa_example2.model.Jugador;
import co.edu.javeriana.jpa_example2.service.JugadorService;

@Controller
@RequestMapping("/jugador")
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/lista")
    public ModelAndView listarJugadores(){
        log.info("Lista de Jugadores");
        List<JugadorDTO> jugadores = jugadorService.listarJugadores();
        ModelAndView modelAndView = new ModelAndView("jugador-lista");
        modelAndView.addObject("listaJugadores",jugadores);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView listarJugador(@PathVariable Long id){
        JugadorDTO jugador = jugadorService.buscarJugador(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("jugador-view");
        modelAndView.addObject("jugador",jugador);
        return modelAndView;
    }

     @GetMapping("/create")
    public ModelAndView formularioCrearJugador() {
        ModelAndView modelAndView = new ModelAndView("jugador-edit"); 
        modelAndView.addObject("jugador", new JugadorDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView formularioEditarJugador(@PathVariable Long id) {
        JugadorDTO jugadorDTO = jugadorService.buscarJugador(id).orElseThrow();
        ModelAndView modelAndView = new ModelAndView("jugador-edit"); 
        modelAndView.addObject("jugador",jugadorDTO);
        return modelAndView;
    }
    
    @PostMapping("/save")
    public RedirectView guardarJugador(@ModelAttribute JugadorDTO jugadorDTO){
        jugadorService.guardarJugador(jugadorDTO);
        return new RedirectView("/jugador/lista");
    }

    @GetMapping("/delete/{id}")
    public RedirectView borrarJugador(@PathVariable Long id) {
        jugadorService.borrarJugador(id);
        return new RedirectView("/jugador/lista");
    }
}
