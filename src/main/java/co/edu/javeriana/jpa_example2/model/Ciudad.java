package co.edu.javeriana.jpa_example2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;
    private int impuesto_entrada;

    @OneToMany(mappedBy = "ciudad_actual")
    private List<Caravana> caravanas = new ArrayList<>();

    @OneToMany(mappedBy = "origen")
    private List<Ruta> rutasOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "destino")
    private List<Ruta> rutasDestino = new ArrayList<>();

    @OneToMany(mappedBy = "ciudad")
    private List<ProductoCiudad> productoCiudades = new ArrayList<>();

    @OneToMany(mappedBy = "ciudad")
    private List<ServicioCiudad> servicioCiudades = new ArrayList<>();

    @OneToMany(mappedBy = "ciudad")
    private List<Transaccion> transacciones = new ArrayList<>();

    public Ciudad() {
    }

    public Ciudad(String nombre, int impuesto_entrada) {
        this.nombre = nombre;
        this.impuesto_entrada = impuesto_entrada;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImpuesto_entrada() {
        return impuesto_entrada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImpuesto_entrada(int impuesto_entrada) {
        this.impuesto_entrada = impuesto_entrada;
    }

    public List<Caravana> getCaravanas() {
        return caravanas;
    }

    public List<Ruta> getRutasOrigen() {
        return rutasOrigen;
    }

    public List<Ruta> getRutasDestino() {
        return rutasDestino;
    }

    public List<ProductoCiudad> getProductoCiudades() {
        return productoCiudades;
    }

    public List<ServicioCiudad> getServicioCiudades() {
        return servicioCiudades;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }
}
