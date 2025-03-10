package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.model.Servicio;

public class ServicioMapper {
    public static ServicioDTO toDTO(Servicio servicio) {
        ServicioDTO servicioDTO = new ServicioDTO();
        servicioDTO.setId(servicio.getId());
        servicioDTO.setTipo(servicio.getTipo());
        return servicioDTO;
    }

    public static Servicio toEntity(ServicioDTO servicioDTO) {
        Servicio servicio = new Servicio();
        servicio.setId(servicioDTO.getId());
        servicio.setTipo(servicioDTO.getTipo());
        return servicio;
    }
}
