package co.edu.javeriana.jpa_example2.dto;

public class ProductoDTO {
    private Long id;
    private String nombre;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
