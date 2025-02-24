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

    @OneToMany(mappedBy = "servicio")
    private List<ServicioCiudad> servicioCiudades = new ArrayList<>();

    @OneToMany(mappedBy = "servicio")
    private List<ServiciosComprados> serviciosComprados = new ArrayList<>();

    public Servicio() {
    }

    public Servicio(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ServicioCiudad> getServicioCiudades() {
        return servicioCiudades;
    }

    public void addServicioCiudad(ServicioCiudad servicioCiudad) {
        this.servicioCiudades.add(servicioCiudad);
        servicioCiudad.setServicio(this);
    }

    public void removeServicioCiudad(ServicioCiudad servicioCiudad) {
        this.servicioCiudades.remove(servicioCiudad);
        servicioCiudad.setServicio(null);
    }

    public List<ServiciosComprados> getServiciosComprados() {
        return serviciosComprados;
    }

    public void addServiciosComprados(ServiciosComprados serviciosComprados) {
        this.serviciosComprados.add(serviciosComprados);
        serviciosComprados.setServicio(this);
    }

    public void removeServiciosComprados(ServiciosComprados serviciosComprados) {
        this.serviciosComprados.remove(serviciosComprados);
        serviciosComprados.setServicio(null);
    }
}
