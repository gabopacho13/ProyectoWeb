package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CaravanaCiudadActualDTO {
    
    private Long ciudadId;
    private List<Long> caravanaIds;

    public CaravanaCiudadActualDTO() {
    }
    
    public CaravanaCiudadActualDTO(List<Long> caravanaIds, Long ciudadId) {
        this.caravanaIds = caravanaIds;
        this.ciudadId = ciudadId;
    }

    public List<Long> getCaravanaIds() {
        return caravanaIds;
    }

    public void setCaravanaIds(List<Long> caravanaIds) {
        this.caravanaIds = caravanaIds;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

}
