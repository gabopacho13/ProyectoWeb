package co.edu.javeriana.jpa_example2.model;

import jakarta.persistence.GenerationType;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransaccionServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tipo;
    private int cantidad;
    private float precio_unitario;
    private Date fecha;

    @ManyToOne
    private Caravana caravana;

    @ManyToOne
    private Servicio servicio;

    @ManyToOne
    private Ciudad ciudad;

    public TransaccionServicio() {
    }

    public TransaccionServicio(String tipo, int cantidad, float precio_unitario, Date fecha, Caravana caravana, Servicio servicio, Ciudad ciudad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.fecha = fecha;
        this.caravana = caravana;
        this.servicio = servicio;
        this.ciudad = ciudad;
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

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Caravana getCaravana() {
        return caravana;
    }

    public void setCaravana(Caravana caravana) {
        this.caravana = caravana;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
