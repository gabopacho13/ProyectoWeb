package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CIudadTransaccionesDTO {

    private Long ciudadId;
    private List<Long> transaccionesIds;

    public CIudadTransaccionesDTO() {
    }

    public CIudadTransaccionesDTO(Long ciudadId, List<Long> transaccionesIds) {
        this.ciudadId = ciudadId;
        this.transaccionesIds = transaccionesIds;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public List<Long> getTransaccionesIds() {
        return transaccionesIds;
    }

    public void setTransaccionesIds(List<Long> transaccionesIds) {
        this.transaccionesIds = transaccionesIds;
    }
}
