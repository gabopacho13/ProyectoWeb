package co.edu.javeriana.jpa_example2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.mapper.PartidaMapper;

import co.edu.javeriana.jpa_example2.repository.PartidaRepository;

@Service
public class PartidaService {
    
    @Autowired
    private PartidaRepository partidaRepository;

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
}
