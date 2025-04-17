package co.edu.javeriana.jpa_example2.dto;

import java.sql.Date;

public class ServiciosCompradosDTO {
    private Long id;
    private Long idServicio;
    private Long idCaravana;
    private Date fechaCompra;

    public ServiciosCompradosDTO() {
    }

    public ServiciosCompradosDTO(Long id, Long idServicio, Long idCarvana, Date fechaCompra) {
        this.id = id;
        this.idServicio = idServicio;
        this.idCaravana = idCarvana;
        this.fechaCompra = fechaCompra;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}