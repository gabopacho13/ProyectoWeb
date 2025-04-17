package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ServiciosComprados {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date fecha_compra;

    @ManyToOne
    private Servicio servicio;

    @ManyToOne
    private Caravana caravana;

    public ServiciosComprados() {
    }

    public ServiciosComprados(Date fecha_compra, Servicio servicio, Caravana caravana) {
        this.fecha_compra = fecha_compra;
        this.servicio = servicio;
        this.caravana = caravana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Caravana getCaravana() {
        return caravana;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }
}
