package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float distancia;
    private boolean es_segura;
    private float dano;
    
    @ManyToOne
    private Ciudad origen;

    @ManyToOne
    private Ciudad destino;

    public Ruta() {
    }

    public Ruta(float distancia, boolean es_segura, float dano, Ciudad origen, Ciudad destino) {
        this.distancia = distancia;
        this.es_segura = es_segura;
        this.dano = dano;
        this.origen = origen;
        this.destino = destino;
    }

    public Ruta(float distancia, boolean es_segura, float dano) {
        this.distancia = distancia;
        this.es_segura = es_segura;
        this.dano = dano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getDistancia() {
        return distancia;
    }

    public boolean getEs_segura() {
        return es_segura;
    }

    public float getDano() {
        return dano;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public void setEs_segura(boolean es_segura) {
        this.es_segura = es_segura;
    }

    public void setDano(float dano) {
        this.dano = dano;
    }

    public Ciudad getOrigen() {
        return origen;
    }

    public Ciudad getDestino() {
        return destino;
    }

    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }

    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }
}
