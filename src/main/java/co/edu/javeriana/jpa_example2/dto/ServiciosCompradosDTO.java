package co.edu.javeriana.jpa_example2.dto;

import java.sql.Date;

public class ServiciosCompradosDTO {
    private Long id;
    private Date fechaCompra;

    public ServiciosCompradosDTO() {
    }

    public ServiciosCompradosDTO(Long id, Date fechaCompra) {
        this.id = id;
        this.fechaCompra = fechaCompra;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}