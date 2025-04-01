package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.mapper.ProductoMapper;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> listarProductos(){
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }

    public Optional<ProductoDTO> buscarProducto(Long id){
        return productoRepository.findById(id)
                .map(ProductoMapper::toDTO);
    }

    public ProductoDTO guardarProducto(ProductoDTO productoDTO){
        productoDTO.setId(null);
        Producto producto = ProductoMapper.toEntity(productoDTO);
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }

    public ProductoDTO actualizarProducto(ProductoDTO productoDTO) {
        if (productoDTO.getId() == null) {
            throw new IllegalArgumentException("El id del producto no puede ser nulo");
        }
        Producto producto = ProductoMapper.toEntity(productoDTO);
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }    

    public void borrarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
