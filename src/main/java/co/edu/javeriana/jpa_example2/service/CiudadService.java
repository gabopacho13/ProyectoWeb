package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.mapper.CiudadMapper;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;

@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;

    public List<CiudadDTO> listarCiudades(){
        return ciudadRepository.findAll().stream()
                .map(CiudadMapper::toDTO)
                .toList();
    }

    public Optional<CiudadDTO> buscarCiudad(Long id){
        return ciudadRepository.findById(id)
                .map(CiudadMapper::toDTO);
    }
}
