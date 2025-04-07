package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.mapper.InventarioCaravanaMapper;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;



@Service
public class InventarioCaravanaService {
    
    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;

    public List<InventarioCaravanaDTO> listarInventarioCaravanas() {
        return inventarioCaravanaRepository.findAll().stream()
                .map(InventarioCaravanaMapper::toDTO)
                .toList();
    }

    public Optional<InventarioCaravanaDTO> buscarInventarioCaravana(Long id) {
        return inventarioCaravanaRepository.findById(id)
                .map(InventarioCaravanaMapper::toDTO);
    }

    public InventarioCaravanaDTO guardarInventarioCaravana(InventarioCaravanaDTO inventarioCaravanaDTO) {
        inventarioCaravanaDTO.setId(null);
        return InventarioCaravanaMapper.toDTO(inventarioCaravanaRepository.save(InventarioCaravanaMapper.toEntity(inventarioCaravanaDTO)));
    }

    public InventarioCaravanaDTO actualizarInventarioCaravana(InventarioCaravanaDTO inventarioCaravanaDTO) {
        if (inventarioCaravanaDTO.getId() == null) {
            throw new IllegalArgumentException("El id del inventario de la caravana no puede ser nulo");
        }
        return InventarioCaravanaMapper.toDTO(inventarioCaravanaRepository.save(InventarioCaravanaMapper.toEntity(inventarioCaravanaDTO)));
    }

    public void borrarInventarioCaravana(Long id) {
        inventarioCaravanaRepository.deleteById(id);
    }
}
