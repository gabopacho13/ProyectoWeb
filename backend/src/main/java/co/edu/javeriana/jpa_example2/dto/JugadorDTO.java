package co.edu.javeriana.jpa_example2.dto;

public class JugadorDTO {
    private Long id;
    private String nombre;
    private String usuario;

    public JugadorDTO() {
    }
   
    public JugadorDTO(Long id, String nombre, String usuario) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public JugadorDTO(String nombre, String usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
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

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
