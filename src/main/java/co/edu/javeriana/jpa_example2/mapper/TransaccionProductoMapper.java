package co.edu.javeriana.jpa_example2.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.dto.TransaccionProductoDTO;
import co.edu.javeriana.jpa_example2.model.TransaccionProducto;
import co.edu.javeriana.jpa_example2.service.CaravanaService;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import co.edu.javeriana.jpa_example2.service.ProductoService;

public class TransaccionProductoMapper {

    @Autowired
    private static CaravanaService caravanaService;
    @Autowired
    private static ProductoService productoService;
    @Autowired
    private static CiudadService ciudadService;

    // Convierte de entidad a DTO
    public static TransaccionProductoDTO toDTO(TransaccionProducto transaccion) {
        if (transaccion == null) {
            return null;
        }
        return new TransaccionProductoDTO(
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
    public static TransaccionProducto toEntity(TransaccionProductoDTO transaccionDTO) {
        if (transaccionDTO == null) {
            return null;
        }
        TransaccionProducto transaccion = new TransaccionProducto();
        transaccion.setId(transaccionDTO.getId());
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