package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.mapper.RutaMapper;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;


@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    public List<RutaDTO> listarRutas(){
        return rutaRepository.findAll().stream()
                .map(RutaMapper::toDTO)
                .toList();
    }

    public Optional<RutaDTO> buscarRutas(Long id){
        return rutaRepository.findById(id)
                .map(RutaMapper::toDTO);
    }

    public void guardarRuta(RutaDTO rutaDTO){//sirve tambien para crear
        Ruta ruta = RutaMapper.toEntity(rutaDTO);
        rutaRepository.save(ruta);
    }

    public void borrarRuta(Long id) {
        rutaRepository.deleteById(id);
    }
}
