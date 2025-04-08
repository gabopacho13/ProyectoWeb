package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.model.ServiciosComprados;
import co.edu.javeriana.jpa_example2.service.ServicioService;
import co.edu.javeriana.jpa_example2.service.CaravanaService;

public class ServiciosCompradosMapper {

    @Autowired
    private static ServicioService servicioService;
    @Autowired
    private static CaravanaService caravanaService;

    // Convierte de entidad a DTO
    public static ServiciosCompradosDTO toDTO(ServiciosComprados serviciosComprados) {
        if (serviciosComprados == null) {
            return null;
        }
        return new ServiciosCompradosDTO(
            serviciosComprados.getId(),
            serviciosComprados.getServicio().getId(),
            serviciosComprados.getCaravana().getId(),
            serviciosComprados.getFecha_compra()
        );
    }

    // Convierte de DTO a entidad
    public static ServiciosComprados toEntity(ServiciosCompradosDTO serviciosCompradosDTO) {
        if (serviciosCompradosDTO == null) {
            return null;
        }
        ServiciosComprados serviciosComprados = new ServiciosComprados();
        serviciosComprados.setServicio(servicioService.buscarServicio(serviciosCompradosDTO.getIdServicio()).map(ServicioMapper::toEntity).orElse(null));
        serviciosComprados.setCaravana(caravanaService.buscarCaravana(serviciosCompradosDTO.getIdCaravana()).map(CaravanaMapper::toEntity).orElse(null));
        serviciosComprados.setFecha_compra(serviciosCompradosDTO.getFechaCompra());
        return serviciosComprados;
    }
}