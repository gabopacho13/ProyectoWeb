package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.CiudadRutasOrigenDTO;
import co.edu.javeriana.jpa_example2.mapper.CiudadMapper;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;

@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private RutaRepository rutaRepository;

    public List<CiudadDTO> listarCiudades(){
        return ciudadRepository.findAll().stream()
                .map(CiudadMapper::toDTO)
                .toList();
    }

    public Optional<CiudadDTO> buscarCiudad(Long id){
        return ciudadRepository.findById(id)
                .map(CiudadMapper::toDTO);
    }

    public CiudadDTO guardarCiudad(CiudadDTO ciudadDTO){//sirve tambien para crear
        ciudadDTO.setId(null);
        Ciudad ciudad = CiudadMapper.toEntity(ciudadDTO);
        return CiudadMapper.toDTO(ciudadRepository.save(ciudad));
    }

    public void borrarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }

    public CiudadDTO actualizarCiudad(CiudadDTO ciudadDTO){
        if (ciudadDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la ciudad no puede ser nulo");
        }
        Ciudad ciudad = CiudadMapper.toEntity(ciudadDTO);
        return CiudadMapper.toDTO(ciudadRepository.save(ciudad));
    }

    public Optional<CiudadRutasOrigenDTO> getCiudadRutasOrigen(Long ciudadId){
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(ciudadId);

        if(ciudadOpt.isEmpty()){
            return Optional.empty();
        }

        Ciudad ciudad = ciudadOpt.get();
        List<Long> rutasIds = ciudad.getRutasOrigen().stream().map(Ruta::getId).toList();

        CiudadRutasOrigenDTO ciudadRutasOrigenDTO = new CiudadRutasOrigenDTO(ciudadId, rutasIds);
        return Optional.of(ciudadRutasOrigenDTO);
    }

    public void updateCiudadRutasOrigen(CiudadRutasOrigenDTO ciudadRutasOrigenDTO){
        Ciudad ciudad = ciudadRepository.findById(ciudadRutasOrigenDTO.getCiudadId()).orElseThrow();
        List<Ruta> selectedRutas = rutaRepository.findAllById(ciudadRutasOrigenDTO.getRutasOrigenIds());
        ciudad.getRutasOrigen().clear();
        ciudad.getRutasOrigen().addAll(selectedRutas);
        ciudadRepository.save(ciudad);
    }
}
