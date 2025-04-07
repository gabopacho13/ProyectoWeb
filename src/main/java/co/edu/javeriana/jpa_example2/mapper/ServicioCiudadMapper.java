package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.model.ServicioCiudad;

public class ServicioCiudadMapper {

    // Convierte de entidad a DTO
    public static ServicioCiudadDTO toDTO(ServicioCiudad servicioCiudad) {
        if (servicioCiudad == null) {
            return null;
        }
        return new ServicioCiudadDTO(
            servicioCiudad.getId(),
            servicioCiudad.getPrecio()
        );
    }

    // Convierte de DTO a entidad
    public static ServicioCiudad toEntity(ServicioCiudadDTO servicioCiudadDTO) {
        if (servicioCiudadDTO == null) {
            return null;
        }
        ServicioCiudad servicioCiudad = new ServicioCiudad();
        servicioCiudad.setPrecio(servicioCiudadDTO.getPrecio());
        return servicioCiudad;
    }
}