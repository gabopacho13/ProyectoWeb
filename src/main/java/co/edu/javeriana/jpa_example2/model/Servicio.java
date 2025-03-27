package co.edu.javeriana.jpa_example2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tipo;
    private String efecto;

    @OneToMany(mappedBy = "servicio")
    private List<ServicioCiudad> servicioCiudades = new ArrayList<>();

    @OneToMany(mappedBy = "servicio")
    private List<ServiciosComprados> serviciosComprados = new ArrayList<>();

    public Servicio() {
    }

    public Servicio(String tipo, String efecto) {
        this.tipo = tipo;
        this.efecto = efecto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public List<ServicioCiudad> getServicioCiudades() {
        return servicioCiudades;
    }

    public List<ServiciosComprados> getServiciosComprados() {
        return serviciosComprados;
    }
}
