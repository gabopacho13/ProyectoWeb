package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.RutaOrigenDTO;
import co.edu.javeriana.jpa_example2.mapper.CiudadMapper;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Ruta;
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

    public CiudadDTO crearCiudad(CiudadDTO ciudadDTO){//sirve tambien para crear
        ciudadDTO.setId(null);
        Ciudad ciudad = CiudadMapper.toEntity(ciudadDTO);
        return CiudadMapper.toDTO(ciudadRepository.save(ciudad));
    }

    public CiudadDTO actualizarCiudad(CiudadDTO ciudadDTO) {
        // chequear que el id sea null
        Ciudad ciudad = CiudadMapper.toEntity(ciudadDTO);
        return CiudadMapper.toDTO(ciudadRepository.save(ciudad));
    }

    public void borrarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }

    public Optional<RutaOrigenDTO> getRutaOrigen(Long ciudadID){
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(ciudadID);

        if(ciudadOpt.isEmpty()){
            return Optional.empty();
        }

        Ciudad ciudad = ciudadOpt.get();
        RutaOrigenDTO rutaOrigenDTO = new RutaOrigenDTO(ciudadID);

        return Optional.of(rutaOrigenDTO);
    }

}
