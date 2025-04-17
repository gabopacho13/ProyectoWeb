package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class PartidaCaravanasDTO {
    
    private Long idPartida;
    private List<Long> caravanasIds;

    public PartidaCaravanasDTO() {
    }

    public PartidaCaravanasDTO(Long idPartida, List<Long> caravanasIds) {
        this.idPartida = idPartida;
        this.caravanasIds = caravanasIds;
    }

    public Long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Long idPartida) {
        this.idPartida = idPartida;
    }

    public List<Long> getCaravanasIds() {
        return caravanasIds;
    }

    public void setCaravanasIds(List<Long> caravanasIds) {
        this.caravanasIds = caravanasIds;
    }

}
