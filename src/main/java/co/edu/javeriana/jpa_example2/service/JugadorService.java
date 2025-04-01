package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.JugadorDTO;
import co.edu.javeriana.jpa_example2.mapper.JugadorMapper;
import co.edu.javeriana.jpa_example2.model.Jugador;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;

@Service
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    public List<JugadorDTO> listarJugadores(){
        return jugadorRepository.findAll().stream()
                .map(JugadorMapper::toDTO)
                .toList();
    }

    public Optional<JugadorDTO> buscarJugador(Long id){
        return jugadorRepository.findById(id)
                .map(JugadorMapper::toDTO);
    }

    public JugadorDTO actualizarJugador(JugadorDTO jugadorDTO){
        if(jugadorDTO.getId() == null){
            throw new IllegalArgumentException("El id del jugador no puede ser nulo");
        }
        Jugador jugador = JugadorMapper.toEntity(jugadorDTO);
        return JugadorMapper.toDTO(jugadorRepository.save(jugador));
    }

    public JugadorDTO guardarJugador(JugadorDTO jugadorDTO){//sirve tambien para crear
        jugadorDTO.setId(null);
        Jugador jugador =JugadorMapper.toEntity(jugadorDTO);
        return JugadorMapper.toDTO(jugadorRepository.save(jugador));
    }

    public void borrarJugador(Long id) {
        jugadorRepository.deleteById(id);
    }
}
