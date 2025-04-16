package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.mapper.InventarioCaravanaMapper;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;

@Service
public class InventarioCaravanaService {
    
    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CaravanaService caravanaService;
    @Autowired
    private ProductoService productoService;

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
        return InventarioCaravanaMapper.toDTO(inventarioCaravanaRepository.save(InventarioCaravanaMapper.toEntity(inventarioCaravanaDTO, caravanaService, productoService)));
    }

    public InventarioCaravanaDTO actualizarInventarioCaravana(InventarioCaravanaDTO inventarioCaravanaDTO) {
        if (inventarioCaravanaDTO.getCaravanaId() == null) {
            throw new IllegalArgumentException("El id del inventario de la caravana no puede ser nulo");
        }
        InventarioCaravana inventarioCaravana = inventarioCaravanaRepository.findById(inventarioCaravanaDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el inventario de la caravana con id: " + inventarioCaravanaDTO.getId()));
        Optional<Caravana> caravanaOptional = caravanaRepository.findById(inventarioCaravanaDTO.getCaravanaId());
        if (caravanaOptional.isPresent()) {
            Caravana caravana = caravanaOptional.get();
            inventarioCaravana.setCaravana(caravana);
        } else {
            throw new IllegalArgumentException("No se encontró la caravana con id: " + inventarioCaravanaDTO.getCaravanaId());
        }
        Optional<Producto> productoOptional = productoRepository.findById(inventarioCaravanaDTO.getProductoId());
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            inventarioCaravana.setProducto(producto);
        } else {
            throw new IllegalArgumentException("No se encontró el producto con id: " + inventarioCaravanaDTO.getProductoId());
        }
        inventarioCaravana.setCantidad(inventarioCaravanaDTO.getCantidad());
        return InventarioCaravanaMapper.toDTO(inventarioCaravanaRepository.save(inventarioCaravana));
    }

    public void borrarInventarioCaravana(Long id) {
        inventarioCaravanaRepository.deleteById(id);
    }

    public List<InventarioCaravanaDTO> buscarInventarioCaravanaPorId(Long id) {
        return caravanaRepository.findById(id)
                .map(Caravana::getInventarioCaravanas)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la caravana con id: " + id))
                .stream()
                .map(InventarioCaravanaMapper::toDTO)
                .toList();
    }
}
