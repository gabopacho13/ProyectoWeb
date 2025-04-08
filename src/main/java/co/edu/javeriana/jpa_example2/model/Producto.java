package co.edu.javeriana.jpa_example2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "producto")
    private List<ProductoCiudad> productoCiudades = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    private List<InventarioCaravana> inventarioCaravanas = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    private List<TransaccionProducto> transaccionesProducto = new ArrayList<>();

    public Producto() {
    }

    public Producto(String nombre) {
        this.nombre = nombre;
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

    public List<ProductoCiudad> getProductoCiudades() {
        return productoCiudades;
    }

    public List<InventarioCaravana> getInventarioCaravanas() {
        return inventarioCaravanas;
    }

    public List<TransaccionProducto> getTransaccionesProducto() {
        return transaccionesProducto;
    }
}
