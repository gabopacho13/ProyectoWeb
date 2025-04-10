package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.CaravanaCiudadDTO;
import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.CiudadRutasDestinoDTO;
import co.edu.javeriana.jpa_example2.dto.CiudadRutasOrigenDTO;
import co.edu.javeriana.jpa_example2.mapper.CiudadMapper;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;

@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;

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

    public Optional<CiudadRutasDestinoDTO> getCiudadRutasDestino(Long ciudadId){
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(ciudadId);
        if(ciudadOpt.isEmpty()){
            return Optional.empty();
        }
    
        Ciudad ciudad = ciudadOpt.get();
        List<Long> rutasIds = ciudad.getRutasDestino().stream().map(Ruta::getId).toList();
        CiudadRutasDestinoDTO ciudadRutasDestinoDTO = new CiudadRutasDestinoDTO(ciudadId, rutasIds);
        return Optional.of(ciudadRutasDestinoDTO);
    }

    public void updateCiudadRutasOrigen(CiudadRutasOrigenDTO ciudadRutasOrigenDTO){
        Ciudad ciudad = ciudadRepository.findById(ciudadRutasOrigenDTO.getCiudadId()).orElseThrow();
        List<Ruta> selectedRutas = rutaRepository.findAllById(ciudadRutasOrigenDTO.getRutasOrigenIds());
        ciudad.getRutasOrigen().clear();
        ciudad.getRutasOrigen().addAll(selectedRutas);
        ciudadRepository.save(ciudad);
    }

    public void updateCiudadRutasDestino(CiudadRutasDestinoDTO ciudadRutasDestinoDTO){
        Ciudad ciudad = ciudadRepository.findById(ciudadRutasDestinoDTO.getCiudadId()).orElseThrow();
        List<Ruta> selectedRutas = rutaRepository.findAllById(ciudadRutasDestinoDTO.getRutasDestinoIds());
        ciudad.getRutasDestino().clear();
        ciudad.getRutasDestino().addAll(selectedRutas);
        ciudadRepository.save(ciudad);
    }

    public CaravanaCiudadDTO listarCaravanasPorCiudad(Long idCiudad){
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(idCiudad);
        if (ciudadOpt.isEmpty()) {
            return null;
        }
        Ciudad ciudad = ciudadOpt.get();
        List<Long> caravanas = ciudad.getCaravanas().stream().map(Caravana::getId).toList();
        return new CaravanaCiudadDTO(ciudad.getId(), caravanas);
    }

    public CaravanaCiudadDTO editarCaravanasPorCiudad(Long idCiudad, List<Long> caravanas) {
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(idCiudad);
        if (ciudadOpt.isEmpty()) {
            return null; // O lanzar una excepción si prefieres
        }
        Ciudad ciudad = ciudadOpt.get();
        List<Caravana> caravanasList = caravanaRepository.findAllById(caravanas).stream().toList();
        for (Caravana caravana : caravanasList) {
            caravana.setCiudad_actual(ciudad);
            caravanaRepository.save(caravana);
        }
        return new CaravanaCiudadDTO(ciudad.getId(), caravanasList.stream().map(Caravana::getId).toList());
    }
}
