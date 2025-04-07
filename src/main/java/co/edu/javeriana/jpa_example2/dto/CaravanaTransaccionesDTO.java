package co.edu.javeriana.jpa_example2.dto;

import java.util.List;

public class CaravanaTransaccionesDTO {
    
    private Long idCaravana;
    private List<Long> transaccionesIds;

    public CaravanaTransaccionesDTO() {
    }

    public CaravanaTransaccionesDTO(Long idCaravana, List<Long> transaccionesIds) {
        this.idCaravana = idCaravana;
        this.transaccionesIds = transaccionesIds;
    }

    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public List<Long> getTransaccionesIds() {
        return transaccionesIds;
    }

    public void setIdTransaccion(List<Long> transaccionesIds) {
        this.transaccionesIds = transaccionesIds;
    }
}
