package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.TransaccionServicioDTO;
import co.edu.javeriana.jpa_example2.mapper.TransaccionServicioMapper;
import co.edu.javeriana.jpa_example2.model.TransaccionServicio;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionServicioRepository;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Servicio;

@Service
public class TransaccionServicioService {
    
    @Autowired
    private TransaccionServicioRepository transaccionRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private CaravanaService caravanaService;
    @Autowired
    private CiudadService ciudadService;
    @Autowired
    private ServicioService servicioService;

    public List<TransaccionServicioDTO> listarTransacciones() {
        return transaccionRepository.findAll().stream()
                .map(TransaccionServicioMapper::toDTO)
                .toList();
    }

    public Optional<TransaccionServicioDTO> buscarTransaccion(Long id) {
        return transaccionRepository.findById(id)
                .map(TransaccionServicioMapper::toDTO);
    }

    public List<TransaccionServicioDTO> buscarTransaccionesPorCaravana(Long idCaravana){
        return caravanaRepository.findById(idCaravana)
                .map(Caravana::getTransaccionesServicio)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la caravana con id: " + idCaravana))
                .stream()
                .map(TransaccionServicioMapper::toDTO)
                .toList();
    }

    public TransaccionServicioDTO guardarTransaccion(TransaccionServicioDTO transaccionDTO) {
        transaccionDTO.setId(null);
        return TransaccionServicioMapper.toDTO(transaccionRepository.save(TransaccionServicioMapper.toEntity(transaccionDTO, caravanaService, ciudadService, servicioService)));
    }

    public TransaccionServicioDTO actualizarTransaccion(TransaccionServicioDTO transaccionDTO) {
        if (transaccionDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la transaccion no puede ser nulo");
        }
        TransaccionServicio transaccion = transaccionRepository.findById(transaccionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la transaccion con id: " + transaccionDTO.getId()));
        Optional<Caravana> caravanaOptional = caravanaRepository.findById(transaccionDTO.getIdCaravana());
        if (caravanaOptional.isPresent()) {
            Caravana caravana = caravanaOptional.get();
            transaccion.setCaravana(caravana);
        } else {
            throw new IllegalArgumentException("No se encontró la caravana con id: " + transaccionDTO.getIdCaravana());
        }
        Optional<Ciudad> ciudadOptional = ciudadRepository.findById(transaccionDTO.getIdCiudad());
        if (ciudadOptional.isPresent()) {
            Ciudad ciudad = ciudadOptional.get();
            transaccion.setCiudad(ciudad);
        } else {
            throw new IllegalArgumentException("No se encontró la ciudad con id: " + transaccionDTO.getIdCiudad());
        }
        Optional<Servicio> servicioOptional = servicioRepository.findById(transaccionDTO.getIdServicio());
        if (servicioOptional.isPresent()) {
            Servicio servicio = servicioOptional.get();
            transaccion.setServicio(servicio);
        } else {
            throw new IllegalArgumentException("No se encontró el servicio con id: " + transaccionDTO.getIdServicio());
        }
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccion.setCantidad(transaccionDTO.getCantidad());
        transaccion.setPrecio_unitario(transaccionDTO.getPrecioUnitario());
        transaccion.setFecha(transaccionDTO.getFecha());
        return TransaccionServicioMapper.toDTO(transaccionRepository.save(transaccion));
    }

    public void borrarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
