package co.edu.javeriana.jpa_example2.dto;

public class InventarioCaravanaDTO {
    private Long id;
    private int cantidad;

    public InventarioCaravanaDTO() {
    }

    public InventarioCaravanaDTO(Long id, int cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}