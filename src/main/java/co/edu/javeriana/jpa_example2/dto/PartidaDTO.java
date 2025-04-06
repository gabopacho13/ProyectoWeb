package co.edu.javeriana.jpa_example2.dto;

import java.time.LocalDateTime;

public class PartidaDTO {
    private Long id;
    private Long tiempoLimite;
    private double gananciaMinima;
    private LocalDateTime tiempoInicio;
    private Long tiempoActual;

    public PartidaDTO() {
    }

    public PartidaDTO(Long id, Long tiempoLimite, double gananciaMinima, LocalDateTime tiempoInicio, Long tiempoActual) {
        this.id = id;
        this.tiempoLimite = tiempoLimite;
        this.gananciaMinima = gananciaMinima;
        this.tiempoInicio = tiempoInicio;
        this.tiempoActual = tiempoActual;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(Long tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public double getGananciaMinima() {
        return gananciaMinima;
    }

    public void setGananciaMinima(double gananciaMinima) {
        this.gananciaMinima = gananciaMinima;
    }

    public LocalDateTime getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(LocalDateTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public Long getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(Long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }
}
