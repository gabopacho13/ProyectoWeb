package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class InventarioCaravana {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int cantidad;

    @ManyToOne
    private Caravana caravana;

    @ManyToOne
    private Producto producto;

    public InventarioCaravana() {
    }

    public InventarioCaravana(int cantidad, Caravana caravana, Producto producto) {
        this.cantidad = cantidad;
        this.caravana = caravana;
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Caravana getCaravana() {
        return caravana;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
