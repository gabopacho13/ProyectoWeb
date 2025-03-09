package co.edu.javeriana.jpa_example2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;

@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tiempoLimite;
    private double gananciaMinima;
    private LocalDateTime tiempoInicio;
    private Long tiempoActual;

    @OneToMany(mappedBy = "partida")
    private List<Caravana> caravanas = new ArrayList<>();

    public Partida() {
        this.tiempoInicio = LocalDateTime.now();
        this.tiempoActual = 0L;
    }

    public Partida(int tiempoLimite, double gananciaMinima) {
        this.tiempoLimite = tiempoLimite;
        this.gananciaMinima = gananciaMinima;
        this.tiempoInicio = LocalDateTime.now();
        this.tiempoActual = 0L;
    }

    public Long getId() {
        return id;
    }

    public Long getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
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

    public List<Caravana> getCaravanas() {
        return caravanas;
    }
}