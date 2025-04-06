package co.edu.javeriana.jpa_example2.dto;

import java.sql.Date;

public class TransaccionDTO {
    private Long id;
    private String tipo;
    private int cantidad;
    private float precioUnitario;
    private Date fecha;

    public TransaccionDTO() {
    }

    public TransaccionDTO(Long id, String tipo, int cantidad, float precioUnitario, Date fecha) {
        this.id = id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
