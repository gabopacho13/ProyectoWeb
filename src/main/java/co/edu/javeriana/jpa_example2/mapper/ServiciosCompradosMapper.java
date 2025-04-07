package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.model.ServiciosComprados;

public class ServiciosCompradosMapper {

    // Convierte de entidad a DTO
    public static ServiciosCompradosDTO toDTO(ServiciosComprados serviciosComprados) {
        if (serviciosComprados == null) {
            return null;
        }
        return new ServiciosCompradosDTO(
            serviciosComprados.getId(),
            serviciosComprados.getFecha_compra()
        );
    }

    // Convierte de DTO a entidad
    public static ServiciosComprados toEntity(ServiciosCompradosDTO serviciosCompradosDTO) {
        if (serviciosCompradosDTO == null) {
            return null;
        }
        ServiciosComprados serviciosComprados = new ServiciosComprados();
        serviciosComprados.setFecha_compra(serviciosCompradosDTO.getFechaCompra());
        return serviciosComprados;
    }
}