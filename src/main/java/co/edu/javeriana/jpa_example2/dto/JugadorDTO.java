package co.edu.javeriana.jpa_example2.dto;

import co.edu.javeriana.jpa_example2.model.Caravana;

public class JugadorDTO {
    private Long id;
    private String nombre;
    private String rol;
    private Caravana caravana;

    public JugadorDTO() {
    }
   
    public JugadorDTO(Long id, String nombre, String rol, Caravana caravana) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.caravana = caravana;
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

    public Caravana getCaravana() {
        return this.caravana;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }


}
