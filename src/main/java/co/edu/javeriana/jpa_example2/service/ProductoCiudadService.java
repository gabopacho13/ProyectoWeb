package co.edu.javeriana.jpa_example2.service;

import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.repository.ProductoCiudadRepository;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.mapper.ProductoCiudadMapper;


@Service
public class ProductoCiudadService {
    
    @Autowired
    private ProductoCiudadRepository productoCiudadRepository;

    public List<ProductoCiudadDTO> listarProductosCiudades() {
        return productoCiudadRepository.findAll().stream()
                .map(ProductoCiudadMapper::toDTO)
                .toList();
    }

    public Optional<ProductoCiudadDTO> buscarProductoCiudad(Long id) {
        return productoCiudadRepository.findById(id)
                .map(ProductoCiudadMapper::toDTO);
    }

    public ProductoCiudadDTO guardarProductoCiudad(ProductoCiudadDTO productoCiudadDTO) {
        productoCiudadDTO.setId(null);
        return ProductoCiudadMapper.toDTO(productoCiudadRepository.save(ProductoCiudadMapper.toEntity(productoCiudadDTO)));
    }

    public ProductoCiudadDTO actualizarProductoCiudad(ProductoCiudadDTO productoCiudadDTO) {
        if (productoCiudadDTO.getId() == null) {
            throw new IllegalArgumentException("El id del producto ciudad no puede ser nulo");
        }
        return ProductoCiudadMapper.toDTO(productoCiudadRepository.save(ProductoCiudadMapper.toEntity(productoCiudadDTO)));
    }

    public void borrarProductoCiudad(Long id) {
        productoCiudadRepository.deleteById(id);
    }
    
}
