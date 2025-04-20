package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;
import co.edu.javeriana.jpa_example2.model.ProductoCiudad;
import org.springframework.stereotype.Component;

@Component
public class CompraventaMapper {

    public ProductoCiudadDTO toProductoCiudadDTO(ProductoCiudad pc) {
        return new ProductoCiudadDTO(
                pc.getId(),
                pc.getCiudad().getId(),
                pc.getProducto().getId(),
                pc.getFactor_demanda(),
                pc.getFactor_oferta(),
                pc.getStock()
        );
    }

    public InventarioCaravanaDTO toInventarioCaravanaDTO(InventarioCaravana ic) {
        return new InventarioCaravanaDTO(
                ic.getId(),
                ic.getCaravana().getId(),
                ic.getProducto().getId(),
                ic.getCantidad()
        );
    }
}