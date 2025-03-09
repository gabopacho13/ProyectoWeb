package co.edu.javeriana.jpa_example2.dto;

public class CiudadDTO {
    private Long id;
    private String nombre;
    private int impuesto_entrada;

    public CiudadDTO() {
    }

    public CiudadDTO(Long id, String nombre, int impuesto_entrada) {
        this.id = id;
        this.nombre = nombre;
        this.impuesto_entrada = impuesto_entrada;
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

    public int getImpuesto_entrada() {
        return this.impuesto_entrada;
    }

    public void setImpuesto_entrada(int impuesto_entrada) {
        this.impuesto_entrada = impuesto_entrada;
    }

}
