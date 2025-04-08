package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.model.ServicioCiudad;
import co.edu.javeriana.jpa_example2.service.ServicioService;
import co.edu.javeriana.jpa_example2.service.CiudadService;

public class ServicioCiudadMapper {

    @Autowired
    private static ServicioService servicioService;
    @Autowired
    private static CiudadService ciudadService;

    // Convierte de entidad a DTO
    public static ServicioCiudadDTO toDTO(ServicioCiudad servicioCiudad) {
        if (servicioCiudad == null) {
            return null;
        }
        return new ServicioCiudadDTO(
            servicioCiudad.getId(),
            servicioCiudad.getServicio().getId(),
            servicioCiudad.getCiudad().getId(),
            servicioCiudad.getPrecio()
        );
    }

    // Convierte de DTO a entidad
    public static ServicioCiudad toEntity(ServicioCiudadDTO servicioCiudadDTO) {
        if (servicioCiudadDTO == null) {
            return null;
        }
        ServicioCiudad servicioCiudad = new ServicioCiudad();
        servicioCiudad.setServicio(servicioService.buscarServicio(servicioCiudadDTO.getIdServicio()).map(ServicioMapper::toEntity).orElse(null));
        servicioCiudad.setCiudad(ciudadService.buscarCiudad(servicioCiudadDTO.getIdCiudad()).map(CiudadMapper::toEntity).orElse(null));
        servicioCiudad.setPrecio(servicioCiudadDTO.getPrecio());
        return servicioCiudad;
    }
}