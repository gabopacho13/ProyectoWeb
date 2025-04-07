package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;

public class InventarioCaravanaMapper {

    // Convierte de entidad a DTO
    public static InventarioCaravanaDTO toDTO(InventarioCaravana inventarioCaravana) {
        if (inventarioCaravana == null) {
            return null;
        }
        return new InventarioCaravanaDTO(
            inventarioCaravana.getId(),
            inventarioCaravana.getCantidad()
        );
    }

    // Convierte de DTO a entidad
    public static InventarioCaravana toEntity(InventarioCaravanaDTO inventarioCaravanaDTO) {
        if (inventarioCaravanaDTO == null) {
            return null;
        }
        InventarioCaravana inventarioCaravana = new InventarioCaravana();
        inventarioCaravana.setCantidad(inventarioCaravanaDTO.getCantidad());
        return inventarioCaravana;
    }
}
