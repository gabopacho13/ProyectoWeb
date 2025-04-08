package co.edu.javeriana.jpa_example2.dto;

public class ProductoCiudadDTO {
    private Long id;
    private Long ciudadId;
    private Long productoId;
    private float factorDemanda;
    private float factorOferta;
    private float stock;

    public ProductoCiudadDTO() {
    }

    public ProductoCiudadDTO(Long id, Long ciudadId, Long productoId, float factorDemanda, float factorOferta, float stock) {
        this.id = id;
        this.ciudadId = ciudadId;
        this.productoId = productoId;
        this.factorDemanda = factorDemanda;
        this.factorOferta = factorOferta;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getFactorDemanda() {
        return factorDemanda;
    }

    public void setFactorDemanda(float factorDemanda) {
        this.factorDemanda = factorDemanda;
    }

    public float getFactorOferta() {
        return factorOferta;
    }

    public void setFactorOferta(float factorOferta) {
        this.factorOferta = factorOferta;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}