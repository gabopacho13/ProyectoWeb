package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CaravanaJugadoresDTO {
    private Long idCaravana;
    private List<Long> jugadoresIds;

    public CaravanaJugadoresDTO() {
    }

    public CaravanaJugadoresDTO(Long idCaravana, List<Long> jugadoresIds) {
        this.idCaravana = idCaravana;
        this.jugadoresIds = jugadoresIds;
    }

    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public List<Long> getJugadoresIds() {
        return jugadoresIds;
    }

    public void setJugadoresIds(List<Long> jugadoresIds) {
        this.jugadoresIds = jugadoresIds;
    }
}
