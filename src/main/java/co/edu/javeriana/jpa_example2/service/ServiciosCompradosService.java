package co.edu.javeriana.jpa_example2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;
import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.mapper.ServiciosCompradosMapper;

@Service
public class ServiciosCompradosService {
    
    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;

    public List<ServiciosCompradosDTO> listarServiciosComprados() {
        return serviciosCompradosRepository.findAll().stream()
                .map(ServiciosCompradosMapper::toDTO)
                .toList();
    }

    public Optional<ServiciosCompradosDTO> buscarServiciosComprados(Long id) {
        return serviciosCompradosRepository.findById(id)
                .map(ServiciosCompradosMapper::toDTO);
    }

    public ServiciosCompradosDTO guardarServicioscomprados(ServiciosCompradosDTO serviciosCompradosDTO) {
        serviciosCompradosDTO.setId(null);
        return ServiciosCompradosMapper.toDTO(serviciosCompradosRepository.save(ServiciosCompradosMapper.toEntity(serviciosCompradosDTO)));
    }

    public ServiciosCompradosDTO actualizarServiciosComprados(ServiciosCompradosDTO serviciosCompradosDTO) {
        if (serviciosCompradosDTO.getId() == null) {
            throw new IllegalArgumentException("El id de los servicios comprados no puede ser nulo");
        }
        return ServiciosCompradosMapper.toDTO(serviciosCompradosRepository.save(ServiciosCompradosMapper.toEntity(serviciosCompradosDTO)));
    }

    public void borrarServiciosComprados(Long id) {
        serviciosCompradosRepository.deleteById(id);
    }
}
