package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.PartidaCaravanasDTO;
import co.edu.javeriana.jpa_example2.service.PartidaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/partida/caravana")
public class PartidaCaravanaController {
    
    @Autowired
    private PartidaService partidaService;

    @GetMapping("/{idPartida}")
    public PartidaCaravanasDTO listarCaravanas(@PathVariable("idPartida") Long idPartida) {
        return partidaService.listarCaravanasPorPartida(idPartida);
    }

   @PostMapping
    public PartidaCaravanasDTO crearPartidas(@RequestBody PartidaCaravanasDTO partidaCaravanasDTO) {
        List<Long> caravanas = partidaCaravanasDTO.getCaravanasIds();
        return partidaService.crearPartidaConCaravanas(caravanas);
    } 

    @PutMapping("/{idPartida}")
    public PartidaCaravanasDTO editarPartidas(@PathVariable("idPartida") Long idPartida, @RequestBody PartidaCaravanasDTO partidaCaravanasDTO) {
        List<Long> caravanas = partidaCaravanasDTO.getCaravanasIds();
        return partidaService.editarCaravanasPorPartida(idPartida, caravanas);
    }
}
