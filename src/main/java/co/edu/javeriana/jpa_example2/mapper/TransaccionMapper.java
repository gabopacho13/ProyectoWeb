package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.TransaccionDTO;
import co.edu.javeriana.jpa_example2.model.Transaccion;

public class TransaccionMapper {

    // Convierte de entidad a DTO
    public static TransaccionDTO toDTO(Transaccion transaccion) {
        if (transaccion == null) {
            return null;
        }
        return new TransaccionDTO(
            transaccion.getId(),
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
        return transaccion;
    }
}