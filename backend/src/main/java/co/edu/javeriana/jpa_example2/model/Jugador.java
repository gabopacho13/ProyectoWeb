package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String usuario;

    @ManyToOne
    private Caravana caravana;

    public Jugador() {
    }

    public Jugador(String nombre, String usuario, Caravana caravana) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.caravana = caravana;
    }

    public Jugador(String nombre, String usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.caravana = null;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Caravana getCaravana() {
        return caravana;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }
}