package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.service.ProductoService;

@Component
public class InventarioCaravanaMapper {

    // Convierte de entidad a DTO
    public static InventarioCaravanaDTO toDTO(InventarioCaravana inventarioCaravana) {
        if (inventarioCaravana == null) {
            return null;
        }
        return new InventarioCaravanaDTO(
            inventarioCaravana.getId(),
            inventarioCaravana.getCaravana().getId(),
            inventarioCaravana.getProducto().getId(),
            inventarioCaravana.getCantidad()
        );
    }

    // Convierte de DTO a entidad
    public static InventarioCaravana toEntity(InventarioCaravanaDTO inventarioCaravanaDTO, CaravanaService caravanaService, ProductoService productoService) {
        if (inventarioCaravanaDTO == null) {
            return null;
        }
        InventarioCaravana inventarioCaravana = new InventarioCaravana();
        inventarioCaravana.setId(inventarioCaravanaDTO.getId());
        inventarioCaravana.setCaravana(caravanaService.buscarCaravana(inventarioCaravanaDTO.getCaravanaId()).map(CaravanaMapper::toEntity).orElse(null));
        inventarioCaravana.setProducto(productoService.buscarProducto(inventarioCaravanaDTO.getProductoId()).map(ProductoMapper::toEntity).orElse(null));
        inventarioCaravana.setCantidad(inventarioCaravanaDTO.getCantidad());
        return inventarioCaravana;
    }
}
