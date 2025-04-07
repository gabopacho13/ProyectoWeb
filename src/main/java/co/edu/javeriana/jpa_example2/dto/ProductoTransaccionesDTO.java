package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class ProductoTransaccionesDTO {
    
    private Long idProducto;
    private List<Long> transaccionesIds;

    public ProductoTransaccionesDTO() {
    }

    public ProductoTransaccionesDTO(Long idProducto, List<Long> transaccionesIds) {
        this.idProducto = idProducto;
        this.transaccionesIds = transaccionesIds;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public List<Long> getTransaccionesIds() {
        return transaccionesIds;
    }

    public void setTransaccionesIds(List<Long> transaccionesIds) {
        this.transaccionesIds = transaccionesIds;
    }
    
}
