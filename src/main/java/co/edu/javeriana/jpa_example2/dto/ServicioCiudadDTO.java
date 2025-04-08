package co.edu.javeriana.jpa_example2.dto;

public class ServicioCiudadDTO {
    private Long id;
    private Long idServicio;
    private Long idCiudad;
    private int precio;

    public ServicioCiudadDTO() {
    }

    public ServicioCiudadDTO(Long id, Long idServicio, Long idCiudad, int precio) {
        this.id = id;
        this.idServicio = idServicio;
        this.idCiudad = idCiudad;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}