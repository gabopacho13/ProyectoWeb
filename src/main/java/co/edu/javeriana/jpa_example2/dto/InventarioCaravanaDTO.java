package co.edu.javeriana.jpa_example2.dto;

public class InventarioCaravanaDTO {
    private Long id;
    private Long caravanaId;
    private Long productoId;
    private int cantidad;

    public InventarioCaravanaDTO() {
    }

    public InventarioCaravanaDTO(Long id, Long caravanaId, Long productoId, int cantidad) {
        this.id = id;
        this.caravanaId = caravanaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaravanaId() {
        return caravanaId;
    }

    public void setCaravanaId(Long caravanaId) {
        this.caravanaId = caravanaId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}