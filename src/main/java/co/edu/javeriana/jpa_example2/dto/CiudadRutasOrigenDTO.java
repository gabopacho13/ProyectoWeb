package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CiudadRutasOrigenDTO {
    private Long ciudadId;
    private List<Long> rutasOrigenIds;

    public CiudadRutasOrigenDTO() {
    }

    public CiudadRutasOrigenDTO(Long ciudadId, List<Long> rutasOrigenIds) {
        this.ciudadId = ciudadId;
        this.rutasOrigenIds = rutasOrigenIds;
    }

    public Long getCiudadId() {
        return this.ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public List<Long> getRutasOrigenIds() {
        return this.rutasOrigenIds;
    }

    public void setRutasOrigenIds(List<Long> rutasOrigenIds) {
        this.rutasOrigenIds = rutasOrigenIds;
    }

}   
