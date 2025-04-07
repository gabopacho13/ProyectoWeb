package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.mapper.ServicioCiudadMapper;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;

@Service
public class ServicioCiudadService {
    
    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;

    public List<ServicioCiudadDTO> listarServicioCiudades() {
        return servicioCiudadRepository.findAll().stream()
                .map(ServicioCiudadMapper::toDTO)
                .toList();
    }

    public Optional<ServicioCiudadDTO> buscarServicioCiudad(Long id) {
        return servicioCiudadRepository.findById(id)
                .map(ServicioCiudadMapper::toDTO);
    }

    public ServicioCiudadDTO guardarServicioCiudad(ServicioCiudadDTO servicioCiudadDTO) {
        servicioCiudadDTO.setId(null);
        return ServicioCiudadMapper.toDTO(servicioCiudadRepository.save(ServicioCiudadMapper.toEntity(servicioCiudadDTO)));
    }

    public ServicioCiudadDTO actualizarServicioCiudad(ServicioCiudadDTO servicioCiudadDTO) {
        if (servicioCiudadDTO.getId() == null) {
            throw new IllegalArgumentException("El id del servicio de la ciudad no puede ser nulo");
        }
        return ServicioCiudadMapper.toDTO(servicioCiudadRepository.save(ServicioCiudadMapper.toEntity(servicioCiudadDTO)));
    }

    public void borrarServicioCiudad(Long id) {
        servicioCiudadRepository.deleteById(id);
    }
}
