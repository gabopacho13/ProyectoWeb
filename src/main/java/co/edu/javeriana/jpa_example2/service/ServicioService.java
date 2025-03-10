package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.mapper.ServicioMapper;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    public List<ServicioDTO> listarServicios() {
        return servicioRepository.findAll().stream()
                .map(ServicioMapper::toDTO)
                .toList();
    }

    public Optional<ServicioDTO> buscarServicio(Long id) {
        return servicioRepository.findById(id)
                .map(ServicioMapper::toDTO);
    }

    public void guardarServicio(ServicioDTO servicioDTO) {
        Servicio servicio = ServicioMapper.toEntity(servicioDTO);
        servicioRepository.save(servicio);
    }

    public void borrarServicio(Long id) {
        servicioRepository.deleteById(id);
    }
}

