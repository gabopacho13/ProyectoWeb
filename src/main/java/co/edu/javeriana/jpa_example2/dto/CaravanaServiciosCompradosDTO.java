package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CaravanaServiciosCompradosDTO {
    
    private Long idCaravana;
    private List<Long> serviciosCompradosIds;

    public CaravanaServiciosCompradosDTO() {
    }

    public CaravanaServiciosCompradosDTO(Long idCaravana, List<Long> serviciosCompradosIds) {
        this.idCaravana = idCaravana;
        this.serviciosCompradosIds = serviciosCompradosIds;
    }

    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public List<Long> getServiciosCompradosIds() {
        return serviciosCompradosIds;
    }

    public void setServiciosCompradosIds(List<Long> serviciosCompradosIds) {
        this.serviciosCompradosIds = serviciosCompradosIds;
    }
}
