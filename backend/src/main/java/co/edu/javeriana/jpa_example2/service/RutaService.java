package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.mapper.RutaMapper;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.mapper.CiudadMapper;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;


@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private CiudadRepository ciudadRepository;

    public List<RutaDTO> listarRutas(){
        return rutaRepository.findAll().stream()
                .map(RutaMapper::toDTO)
                .toList();
    }

    public Optional<RutaDTO> buscarRutas(Long id){
        return rutaRepository.findById(id)
                .map(RutaMapper::toDTO);
    }

    public Optional<List<CiudadDTO>> obtenerConexiones(Long id){
        Ruta ruta = rutaRepository.findById(id).orElseThrow();
        Ciudad origen = ciudadRepository.findById(ruta.getOrigen().getId()).orElseThrow();
        Ciudad destino = ciudadRepository.findById(ruta.getDestino().getId()).orElseThrow();
        List<Long> conexionesIds = new ArrayList<>();
        conexionesIds.add(origen.getId());
        conexionesIds.add(destino.getId());
        List<CiudadDTO> conexiones = ciudadRepository.findAllById(conexionesIds).stream()
                .map(CiudadMapper::toDTO)
                .toList();
        return Optional.of(conexiones);
    }

    public RutaDTO  guardarRuta(RutaDTO rutaDTO){//sirve tambien para crear
        rutaDTO.setId(null);
        Ruta ruta = RutaMapper.toEntity(rutaDTO);
        return RutaMapper.toDTO(rutaRepository.save(ruta));
    }

    public RutaDTO actualizarRuta(RutaDTO rutaDTO) {
        if (rutaDTO.getId() == null) {
            throw new IllegalArgumentException("El ID de la ruta no puede ser nulo");
        }
        Ruta ruta = RutaMapper.toEntity(rutaDTO);
        return RutaMapper.toDTO(rutaRepository.save(ruta));
    }

    public void borrarRuta(Long id) {
        rutaRepository.deleteById(id);
    }
}
