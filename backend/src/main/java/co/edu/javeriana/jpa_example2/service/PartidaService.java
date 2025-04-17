package co.edu.javeriana.jpa_example2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.javeriana.jpa_example2.dto.PartidaCaravanasDTO;
import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.mapper.PartidaMapper;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Partida;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;

@Service
public class PartidaService {
    
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;

    public List<PartidaDTO> listarPartidas() {
        return partidaRepository.findAll().stream()
                .map(PartidaMapper::toDTO)
                .toList();
    }

    public Optional<PartidaDTO> buscarPartida(Long id) {
        return partidaRepository.findById(id)
                .map(PartidaMapper::toDTO);
    }

    public PartidaDTO guardarPartida(PartidaDTO partidaDTO) {
        partidaDTO.setId(null);
        return PartidaMapper.toDTO(partidaRepository.save(PartidaMapper.toEntity(partidaDTO)));
    }

    public PartidaDTO actualizarPartida(PartidaDTO partidaDTO) {
        if (partidaDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la partida no puede ser nulo");
        }
        return PartidaMapper.toDTO(partidaRepository.save(PartidaMapper.toEntity(partidaDTO)));
    }

    public void borrarPartida(Long id) {
        partidaRepository.deleteById(id);
    }

    public PartidaCaravanasDTO listarCaravanasPorPartida(Long idPartida) {
        Optional<Partida> partidaOpt = partidaRepository.findById(idPartida);
        if (partidaOpt.isEmpty()) {
            return null; // O lanzar una excepción si prefieres
        }
        Partida partida = partidaOpt.get();
        List<Long> caravanas = partida.getCaravanas().stream().map(Caravana::getId).toList(); // Asumiendo que tienes un método getCaravanas en Partida
        return new PartidaCaravanasDTO(partida.getId(), caravanas);
    }

    public PartidaCaravanasDTO editarCaravanasPorPartida(Long idPartida, List<Long> caravanas) {
        Optional<Partida> partidaOptional = partidaRepository.findById(idPartida);
        if (partidaOptional.isEmpty()) {
            return null; // O lanzar una excepción si prefieres
        }
        List<Caravana> caravanasList = caravanaRepository.findAllById(caravanas);
        for (Caravana caravana : caravanasList) {
            caravana.setPartida(partidaOptional.get());
            caravanaRepository.save(caravana);
        }
        return new PartidaCaravanasDTO(partidaOptional.get().getId(), caravanasList.stream().map(Caravana::getId).toList());
    }
}
