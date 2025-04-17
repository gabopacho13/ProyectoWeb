package co.edu.javeriana.jpa_example2.dto;

public class RutaDTO {
    private Long id;
    private float distancia;
    private boolean es_segura;
    private float dano;

    public RutaDTO() {
    }

    public RutaDTO(Long id, float distancia, boolean es_segura, float dano) {
        this.id = id;
        this.distancia = distancia;
        this.es_segura = es_segura;
        this.dano = dano;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getDistancia() {
        return this.distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public boolean isEs_segura() {
        return this.es_segura;
    }

    public boolean getEs_segura() {
        return this.es_segura;
    }

    public void setEs_segura(boolean es_segura) {
        this.es_segura = es_segura;
    }

    public float getDano() {
        return this.dano;
    }

    public void setDano(float dano) {
        this.dano = dano;
    }

}
