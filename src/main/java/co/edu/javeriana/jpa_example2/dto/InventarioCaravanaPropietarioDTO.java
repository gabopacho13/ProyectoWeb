package co.edu.javeriana.jpa_example2.dto;

public class InventarioCaravanaPropietarioDTO {

    private int cantidad;
    private Long idCaravanaPropietario;

    public InventarioCaravanaPropietarioDTO() {
    }

    public InventarioCaravanaPropietarioDTO(int cantidad, Long idCaravanaPropietario) {
        this.cantidad = cantidad;
        this.idCaravanaPropietario = idCaravanaPropietario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdCaravanaPropietario() {
        return idCaravanaPropietario;
    }

    public void setIdCaravanaPropietario(Long idCaravanaPropietario) {
        this.idCaravanaPropietario = idCaravanaPropietario;
    }
}
