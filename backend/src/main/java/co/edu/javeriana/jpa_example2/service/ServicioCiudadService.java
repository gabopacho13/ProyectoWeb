package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.mapper.ServicioCiudadMapper;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.model.ServicioCiudad;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.Ciudad;

@Service
public class ServicioCiudadService {
    
    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private CiudadService ciudadService;

    public List<ServicioCiudadDTO> listarServicioCiudades() {
        return servicioCiudadRepository.findAll().stream()
                .map(ServicioCiudadMapper::toDTO)
                .toList();
    }

    public Optional<ServicioCiudadDTO> buscarServicioCiudad(Long id) {
        return servicioCiudadRepository.findById(id)
                .map(ServicioCiudadMapper::toDTO);
    }

    public List<ServicioCiudadDTO> buscarServicioCiudadPorCiudad(Long idCiudad) {
        return ciudadRepository.findById(idCiudad)
                .map(Ciudad :: getServicioCiudades)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la ciudad con id: " + idCiudad))
                .stream()
                .map(ServicioCiudadMapper::toDTO)
                .toList();
    }

    public ServicioCiudadDTO guardarServicioCiudad(ServicioCiudadDTO servicioCiudadDTO) {
        servicioCiudadDTO.setId(null);
        return ServicioCiudadMapper.toDTO(servicioCiudadRepository.save(ServicioCiudadMapper.toEntity(servicioCiudadDTO, servicioService, ciudadService)));
    }

    public ServicioCiudadDTO actualizarServicioCiudad(ServicioCiudadDTO servicioCiudadDTO) {
        if (servicioCiudadDTO.getId() == null) {
            throw new IllegalArgumentException("El id del servicio de la ciudad no puede ser nulo");
        }
        ServicioCiudad servicioCiudad = servicioCiudadRepository.findById(servicioCiudadDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el servicio de la ciudad con id: " + servicioCiudadDTO.getId()));
        Optional<Servicio> servicioOptional = servicioRepository.findById(servicioCiudadDTO.getIdServicio());
        if (servicioOptional.isPresent()) {
            Servicio servicio = servicioOptional.get();
            servicioCiudad.setServicio(servicio);
        } else {
            throw new IllegalArgumentException("No se encontró el servicio con id: " + servicioCiudadDTO.getIdServicio());
        }
        Optional<Ciudad> ciudadOptional = ciudadRepository.findById(servicioCiudadDTO.getIdCiudad());
        if (ciudadOptional.isPresent()) {
            Ciudad ciudad = ciudadOptional.get();
            servicioCiudad.setCiudad(ciudad);
        } else {
            throw new IllegalArgumentException("No se encontró la ciudad con id: " + servicioCiudadDTO.getIdCiudad());
        }
        servicioCiudad.setPrecio(servicioCiudadDTO.getPrecio());
        return ServicioCiudadMapper.toDTO(servicioCiudadRepository.save(servicioCiudad));
    }

    public void borrarServicioCiudad(Long id) {
        servicioCiudadRepository.deleteById(id);
    }
}
