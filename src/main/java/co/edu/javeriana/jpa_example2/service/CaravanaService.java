package co.edu.javeriana.jpa_example2.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CaravanaDTO;
import co.edu.javeriana.jpa_example2.dto.CaravanaJugadoresDTO;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.mapper.CaravanaMapper;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Jugador;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;

import java.util.Optional;

@Service
public class CaravanaService {

    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    public List<CaravanaDTO> listarCaravanas() {
        return caravanaRepository.findAll().stream()
                .map(CaravanaMapper::toDTO)
                .toList();
    }

    public Optional<CaravanaDTO> buscarCaravana(Long id) {
        return caravanaRepository.findById(id)
                .map(CaravanaMapper::toDTO);
    }

    public CaravanaDTO guardarCaravana(CaravanaDTO caravanaDTO) {
        caravanaDTO.setId(null);
        return CaravanaMapper.toDTO(caravanaRepository.save(CaravanaMapper.toEntity(caravanaDTO)));
    }

    public void borrarCaravana(Long id) {
        caravanaRepository.deleteById(id);
    }

    public CaravanaDTO actualizarCaravana(CaravanaDTO caravanaDTO) {
        if (caravanaDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la caravana no puede ser nulo");
        }
        return CaravanaMapper.toDTO(caravanaRepository.save(CaravanaMapper.toEntity(caravanaDTO)));
    }

    public CaravanaJugadoresDTO listarJugadoresPorCaravana(Long idCaravana) {
        Optional<Caravana> caravanaOpt = caravanaRepository.findById(idCaravana);
        if (caravanaOpt.isEmpty()) {
            return null; // O lanzar una excepción si prefieres
        }
        Caravana caravana = caravanaOpt.get();
        List<Long> jugadores = caravana.getJugadores().stream().map(Jugador::getId).toList(); // Asumiendo que tienes un método getJugadores en Caravana
        return new CaravanaJugadoresDTO(caravana.getId(), jugadores);
    }

    public CaravanaJugadoresDTO editarJugadoresPorCaravana(Long idCaravana, List<Long> jugadores) {
        Optional<Caravana> caravanaOpt = caravanaRepository.findById(idCaravana);
        if (caravanaOpt.isEmpty()) {
            return null; // O lanzar una excepción si prefieres
        }
        List<Jugador> jugadoresList = jugadorRepository.findAllById(jugadores);
        for (Jugador jugador : jugadoresList) {
            jugador.setCaravana(caravanaOpt.get());
            jugadorRepository.save(jugador);
        }
        return new CaravanaJugadoresDTO(caravanaOpt.get().getId(), jugadoresList.stream().map(Jugador::getId).toList());
    }
}
