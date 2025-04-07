package co.edu.javeriana.jpa_example2.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.CaravanaDTO;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.mapper.CaravanaMapper;
import java.util.Optional;

@Service
public class CaravanaService {

    @Autowired
    private CaravanaRepository caravanaRepository;

    public List<CaravanaDTO> listarCaravanas() {
        return caravanaRepository.findAll().stream()
                .map(CaravanaMapper::toDTO)
                .toList();
    }

    public Optional<CaravanaDTO> buscarCaravana(Long id) {
        return caravanaRepository.findById(id)
                .map(CaravanaMapper::toDTO);
    }

    public CaravanaDTO guardarCaravana(CaravanaDTO caravanaDTO) {
        caravanaDTO.setId(null);
        return CaravanaMapper.toDTO(caravanaRepository.save(CaravanaMapper.toEntity(caravanaDTO)));
    }

    public void borrarCaravana(Long id) {
        caravanaRepository.deleteById(id);
    }

    public CaravanaDTO actualizarCaravana(CaravanaDTO caravanaDTO) {
        if (caravanaDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la caravana no puede ser nulo");
        }
        return CaravanaMapper.toDTO(caravanaRepository.save(CaravanaMapper.toEntity(caravanaDTO)));
    }
}
