package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CiudadProductoCiudadesDTO {
    
    private Long ciudadId;
    private List<Long> ciudadProductosIds;

    public CiudadProductoCiudadesDTO() {
    }

    public CiudadProductoCiudadesDTO(Long ciudadId, List<Long> ciudadProductosIds) {
        this.ciudadId = ciudadId;
        this.ciudadProductosIds = ciudadProductosIds;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public List<Long> getCiudadProductosIds() {
        return ciudadProductosIds;
    }

    public void setCiudadProductosIds(List<Long> ciudadProductosIds) {
        this.ciudadProductosIds = ciudadProductosIds;
    }
}
