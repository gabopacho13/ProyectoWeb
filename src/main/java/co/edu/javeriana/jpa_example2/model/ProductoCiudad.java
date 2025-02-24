package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductoCiudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    float factor_demanda;
    float factor_oferta;
    float stock;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Ciudad ciudad;

    public ProductoCiudad() {
    }

    public ProductoCiudad(float factor_demanda, float factor_oferta, float stock, Producto producto, Ciudad ciudad) {
        this.factor_demanda = factor_demanda;
        this.factor_oferta = factor_oferta;
        this.stock = stock;
        this.producto = producto;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public float getFactor_demanda() {
        return factor_demanda;
    }

    public float getFactor_oferta() {
        return factor_oferta;
    }

    public float getStock() {
        return stock;
    }

    public void setFactor_demanda(float factor_demanda) {
        this.factor_demanda = factor_demanda;
    }

    public void setFactor_oferta(float factor_oferta) {
        this.factor_oferta = factor_oferta;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
