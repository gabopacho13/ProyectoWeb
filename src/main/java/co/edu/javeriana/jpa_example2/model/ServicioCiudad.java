package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServicioCiudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int precio;
    private String efecto;

    @ManyToOne
    private Servicio servicio;

    @ManyToOne
    private Ciudad ciudad;

    public ServicioCiudad() {
    }

    public ServicioCiudad(int precio, String efecto, Servicio servicio, Ciudad ciudad) {
        this.precio = precio;
        this.efecto = efecto;
        this.servicio = servicio;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public int getPrecio() {
        return precio;
    }

    public String getEfecto() {
        return efecto;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
