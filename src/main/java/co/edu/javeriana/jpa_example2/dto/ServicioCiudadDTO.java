package co.edu.javeriana.jpa_example2.dto;

public class ServicioCiudadDTO {
    private Long id;
    private int precio;

    public ServicioCiudadDTO() {
    }

    public ServicioCiudadDTO(Long id, int precio) {
        this.id = id;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}