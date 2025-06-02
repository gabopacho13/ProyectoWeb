package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CaravanaCiudadDTO {
    private Long idCiudad;
    private List<Long> caravanasIds;

    public CaravanaCiudadDTO() {
    }

    public CaravanaCiudadDTO(Long idCiudad, List<Long> caravanasIds) {
        this.idCiudad = idCiudad;
        this.caravanasIds = caravanasIds;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public List<Long> getCaravanasIds() {
        return caravanasIds;
    }

    public void setCaravanasIds(List<Long> caravanasIds) {
        this.caravanasIds = caravanasIds;
    }

    public Object getCiudadId() {
        return idCiudad;
    }
}
