package co.edu.javeriana.jpa_example2.dto;

public class JugadorDTO {
    private Long id;
    private String nombre;
    private String rol;

    public JugadorDTO() {
    }
   
    public JugadorDTO(Long id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public JugadorDTO(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
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

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
