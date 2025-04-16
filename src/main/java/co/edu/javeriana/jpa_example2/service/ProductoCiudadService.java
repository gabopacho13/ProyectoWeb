package co.edu.javeriana.jpa_example2.service;

import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.mapper.ProductoCiudadMapper;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.ProductoCiudad;


@Service
public class ProductoCiudadService {
    
    @Autowired
    private ProductoCiudadRepository productoCiudadRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CiudadService ciudadService;

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
        return ProductoCiudadMapper.toDTO(productoCiudadRepository.save(ProductoCiudadMapper.toEntity(productoCiudadDTO, productoService, ciudadService)));
    }

    public ProductoCiudadDTO actualizarProductoCiudad(ProductoCiudadDTO productoCiudadDTO) {
        if (productoCiudadDTO.getId() == null) {
            throw new IllegalArgumentException("El id del producto ciudad no puede ser nulo");
        }
        ProductoCiudad productoCiudad = ProductoCiudadMapper.toEntity(productoCiudadDTO, productoService, ciudadService);
        Optional<Producto> productoOpt = productoRepository.findById(productoCiudadDTO.getProductoId());
        if (productoOpt.isPresent()){
            Producto producto = productoOpt.get();
            productoCiudad.setProducto(producto);
        } else {
            throw new IllegalArgumentException("No se encontró el producto con id: " + productoCiudadDTO.getProductoId());
        }
        Optional<Ciudad> ciudadOpt = ciudadRepository.findById(productoCiudadDTO.getCiudadId());
        if (ciudadOpt.isPresent()){
            Ciudad ciudad = ciudadOpt.get();
            productoCiudad.setCiudad(ciudad);
        } else {
            throw new IllegalArgumentException("No se encontró la ciudad con id: " + productoCiudadDTO.getCiudadId());
        }
        productoCiudad.setFactor_oferta(productoCiudadDTO.getFactorOferta());
        productoCiudad.setFactor_demanda(productoCiudadDTO.getFactorDemanda());
        productoCiudad.setStock(productoCiudadDTO.getStock());
        return ProductoCiudadMapper.toDTO(productoCiudadRepository.save(productoCiudad));
    }

    public void borrarProductoCiudad(Long id) {
        productoCiudadRepository.deleteById(id);
    }
    
    public List<ProductoCiudadDTO> buscarPorCiudadId(Long ciudadId) {
        return ciudadRepository.findById(ciudadId)
                .map(Ciudad::getProductoCiudades)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la ciudad con id: " + ciudadId))
                .stream()
                .map(ProductoCiudadMapper::toDTO)
                .toList();
    }
}
