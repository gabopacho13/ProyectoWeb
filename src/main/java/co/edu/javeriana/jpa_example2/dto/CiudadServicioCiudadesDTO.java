package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CiudadServicioCiudadesDTO {
    
    private Long idCiudad;
    private List<Long> ciudadServicioIds;

    public CiudadServicioCiudadesDTO() {
    }

    public CiudadServicioCiudadesDTO(Long idCiudad, List<Long> ciudadServicioIds) {
        this.idCiudad = idCiudad;
        this.ciudadServicioIds = ciudadServicioIds;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public List<Long> getCiudadServicioIds() {
        return ciudadServicioIds;
    }

    public void setCiudadServicioIds(List<Long> ciudadServicioIds) {
        this.ciudadServicioIds = ciudadServicioIds;
    }

}
