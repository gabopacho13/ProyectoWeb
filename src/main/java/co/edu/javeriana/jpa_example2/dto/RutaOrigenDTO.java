package co.edu.javeriana.jpa_example2.dto;

public class RutaOrigenDTO {
    private Long rutaID;
    private Long ciudadID;

    public RutaOrigenDTO() {
    }

    public RutaOrigenDTO(Long ciudadID) {
        this.ciudadID = ciudadID;
    }

    public Long getRutaID() {
        return this.rutaID;
    }

    public void setRutaID(Long rutaID) {
        this.rutaID = rutaID;
    }

    public Long getCiudadID() {
        return this.ciudadID;
    }

    public void setCiudadID(Long ciudadID) {
        this.ciudadID = ciudadID;
    }

}
