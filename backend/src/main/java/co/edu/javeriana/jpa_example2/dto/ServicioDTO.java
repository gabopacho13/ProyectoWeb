package co.edu.javeriana.jpa_example2.dto;

public class ServicioDTO {
    private Long id;
    private String tipo;
    private String efecto;

    public ServicioDTO() {
    }

    public ServicioDTO(Long id, String tipo, String efecto) {
        this.id = id;
        this.tipo = tipo;
        this.efecto = efecto;
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


    public String getEfecto() {
        return this.efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }
}
