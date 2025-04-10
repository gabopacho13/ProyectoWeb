package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.TransaccionServicioDTO;
import co.edu.javeriana.jpa_example2.model.TransaccionServicio;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.ServicioService;

public class TransaccionServicioMapper {

    @Autowired
    private static CaravanaService caravanaService;
    @Autowired
    private static ServicioService servicioService;
    @Autowired
    private static CiudadService ciudadService;

    // Convierte de entidad a DTO
    public static TransaccionServicioDTO toDTO(TransaccionServicio transaccion) {
        if (transaccion == null) {
            return null;
        }
        return new TransaccionServicioDTO(
            transaccion.getId(),
            transaccion.getCaravana().getId(),
            transaccion.getServicio().getId(),
            transaccion.getCiudad().getId(),
            transaccion.getTipo(),
            transaccion.getCantidad(),
            transaccion.getPrecio_unitario(),
            transaccion.getFecha()
        );
    }

    // Convierte de DTO a entidad
    public static TransaccionServicio toEntity(TransaccionServicioDTO transaccionDTO) {
        if (transaccionDTO == null) {
            return null;
        }
        TransaccionServicio transaccion = new TransaccionServicio();
        transaccion.setId(transaccionDTO.getId());
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccion.setCantidad(transaccionDTO.getCantidad());
        transaccion.setPrecio_unitario(transaccionDTO.getPrecioUnitario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setCaravana(caravanaService.buscarCaravana(transaccionDTO.getIdCaravana()).map(CaravanaMapper::toEntity).orElse(null));
        transaccion.setCiudad(ciudadService.buscarCiudad(transaccionDTO.getIdCiudad()).map(CiudadMapper::toEntity).orElse(null));
        transaccion.setServicio(servicioService.buscarServicio(transaccionDTO.getIdServicio()).map(ServicioMapper::toEntity).orElse(null));
        return transaccion;
    }
}