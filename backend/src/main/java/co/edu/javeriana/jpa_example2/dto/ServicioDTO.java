package co.edu.javeriana.jpa_example2.dto;

public class ServicioDTO {
    private Long id;
    private String tipo;

    public ServicioDTO() {
    }

    public ServicioDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
