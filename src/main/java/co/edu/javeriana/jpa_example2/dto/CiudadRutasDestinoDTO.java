package co.edu.javeriana.jpa_example2.dto;

import java.util.List;


public class CiudadRutasDestinoDTO {
    private Long ciudadId;
    private List<Long> rutasDestinoIds;

    public CiudadRutasDestinoDTO() {
    }

    public CiudadRutasDestinoDTO(Long ciudadId, List<Long> rutasDestinoIds) {
        this.ciudadId = ciudadId;
        this.rutasDestinoIds = rutasDestinoIds;
    }

    public Long getCiudadId() {
        return this.ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public List<Long> getRutasDestinoIds() {
        return this.rutasDestinoIds;
    }

    public void setRutasDestinoIds(List<Long> rutasDestinoIds) {
        this.rutasDestinoIds = rutasDestinoIds;
    }
}
