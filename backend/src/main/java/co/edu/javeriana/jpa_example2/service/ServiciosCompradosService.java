package co.edu.javeriana.jpa_example2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;
import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.mapper.ServiciosCompradosMapper;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.model.ServiciosComprados;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.Caravana;

@Service
public class ServiciosCompradosService {
    
    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private CaravanaService caravanaService;

    public List<ServiciosCompradosDTO> listarServiciosComprados() {
        return serviciosCompradosRepository.findAll().stream()
                .map(ServiciosCompradosMapper::toDTO)
                .toList();
    }

    public Optional<ServiciosCompradosDTO> buscarServiciosComprados(Long id) {
        return serviciosCompradosRepository.findById(id)
                .map(ServiciosCompradosMapper::toDTO);
    }

    public List<ServiciosCompradosDTO> buscarServiciosCompradosPorCaravana(Long idCaravana) {
        return caravanaRepository.findById(idCaravana)
                .map(Caravana::getServiciosComprados)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la caravana con id: " + idCaravana))
                .stream()
                .map(ServiciosCompradosMapper::toDTO)
                .toList();
    }

    public ServiciosCompradosDTO guardarServicioscomprados(ServiciosCompradosDTO serviciosCompradosDTO) {
        serviciosCompradosDTO.setId(null);
        return ServiciosCompradosMapper.toDTO(serviciosCompradosRepository.save(ServiciosCompradosMapper.toEntity(serviciosCompradosDTO, servicioService, caravanaService)));
    }

    public ServiciosCompradosDTO actualizarServiciosComprados(ServiciosCompradosDTO serviciosCompradosDTO) {
        if (serviciosCompradosDTO.getId() == null) {
            throw new IllegalArgumentException("El id de los servicios comprados no puede ser nulo");
        }
        ServiciosComprados serviciosComprados = serviciosCompradosRepository.findById(serviciosCompradosDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el servicio comprado con id: " + serviciosCompradosDTO.getId()));
        Optional<Servicio> servicioOptional = servicioRepository.findById(serviciosCompradosDTO.getIdServicio());
        if (servicioOptional.isPresent()) {
            Servicio servicio = servicioOptional.get();
            serviciosComprados.setServicio(servicio);
        } else {
            throw new IllegalArgumentException("No se encontró el servicio con id: " + serviciosCompradosDTO.getIdServicio());
        }
        Optional<Caravana> caravanaOptional = caravanaRepository.findById(serviciosCompradosDTO.getIdCaravana());
        if (caravanaOptional.isPresent()){
            Caravana caravana = caravanaOptional.get();
            serviciosComprados.setCaravana(caravana);
        } else {
            throw new IllegalArgumentException("No se encontró la caravana con id: " + serviciosCompradosDTO.getIdCaravana());
        }
        serviciosComprados.setFecha_compra(serviciosCompradosDTO.getFechaCompra());
        return ServiciosCompradosMapper.toDTO(serviciosCompradosRepository.save(serviciosComprados));
    }

    public void borrarServiciosComprados(Long id) {
        serviciosCompradosRepository.deleteById(id);
    }
}
