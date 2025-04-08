package co.edu.javeriana.jpa_example2.dto;

import java.sql.Date;

public class TransaccionProductoDTO {
    private Long id;
    private Long idCaravana;
    private Long idProducto;
    private Long idCiudad;
    private String tipo;
    private int cantidad;
    private float precioUnitario;
    private Date fecha;

    public TransaccionProductoDTO() {
    }

    public TransaccionProductoDTO(Long id, Long idCaravana, Long idProducto, Long idCiudad, String tipo, int cantidad, float precioUnitario, Date fecha) {
        this.id = id;
        this.idCaravana = idCaravana;
        this.idProducto = idProducto;
        this.idCiudad = idCiudad;
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

    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Long idCiudad) {
        this.idCiudad = idCiudad;
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
