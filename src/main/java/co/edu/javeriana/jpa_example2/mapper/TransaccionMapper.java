package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.TransaccionDTO;
import co.edu.javeriana.jpa_example2.model.Transaccion;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.ProductoService;

public class TransaccionMapper {

    @Autowired
    private static CaravanaService caravanaService;
    @Autowired
    private static ProductoService productoService;
    @Autowired
    private static CiudadService ciudadService;

    // Convierte de entidad a DTO
    public static TransaccionDTO toDTO(Transaccion transaccion) {
        if (transaccion == null) {
            return null;
        }
        return new TransaccionDTO(
            transaccion.getId(),
            transaccion.getCaravana().getId(),
            transaccion.getProducto().getId(),
            transaccion.getCiudad().getId(),
            transaccion.getTipo(),
            transaccion.getCantidad(),
            transaccion.getPrecio_unitario(),
            transaccion.getFecha()
        );
    }

    // Convierte de DTO a entidad
    public static Transaccion toEntity(TransaccionDTO transaccionDTO) {
        if (transaccionDTO == null) {
            return null;
        }
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccion.setCantidad(transaccionDTO.getCantidad());
        transaccion.setPrecio_unitario(transaccionDTO.getPrecioUnitario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setCaravana(caravanaService.buscarCaravana(transaccionDTO.getIdCaravana()).map(CaravanaMapper::toEntity).orElse(null));
        transaccion.setCiudad(ciudadService.buscarCiudad(transaccionDTO.getIdCiudad()).map(CiudadMapper::toEntity).orElse(null));
        transaccion.setProducto(productoService.buscarProducto(transaccionDTO.getIdProducto()).map(ProductoMapper::toEntity).orElse(null));
        return transaccion;
    }
}