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
    private List<Transaccion> transacciones = new ArrayList<>();

    public Producto() {
    }

    public Producto(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
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

    public void addProductoCiudad(ProductoCiudad productoCiudad) {
        this.productoCiudades.add(productoCiudad);
        productoCiudad.setProducto(this);
    }

    public void removeProductoCiudad(ProductoCiudad productoCiudad) {
        this.productoCiudades.remove(productoCiudad);
        productoCiudad.setProducto(null);
    }

    public List<InventarioCaravana> getInventarioCaravanas() {
        return inventarioCaravanas;
    }

    public void addInventarioCaravana(InventarioCaravana inventarioCaravana) {
        this.inventarioCaravanas.add(inventarioCaravana);
        inventarioCaravana.setProducto(this);
    }

    public void removeInventarioCaravana(InventarioCaravana inventarioCaravana) {
        this.inventarioCaravanas.remove(inventarioCaravana);
        inventarioCaravana.setProducto(null);
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void addTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
        transaccion.setProducto(this);
    }

    public void removeTransaccion(Transaccion transaccion){
        this.transacciones.remove(transaccion);
        transaccion.setProducto(null);
    }
}
