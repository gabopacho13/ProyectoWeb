package co.edu.javeriana.jpa_example2.dto;

import java.time.LocalDateTime;

public class CaravanaDTO {
    private Long id;
    private LocalDateTime fechaCreacion;
    private String nombre;
    private float velocidadBase;
    private float velocidadActual;
    private float capacidadBase;
    private float capacidadActual;
    private int dinero;
    private float saludActual;
    private float saludMaxima;
    private Long tiempoAcumulado;
    private boolean tieneGuardias;

    public CaravanaDTO() {
    }

    public CaravanaDTO(Long id, LocalDateTime fechaCreacion, String nombre, float velocidadBase, float velocidadActual,
                       float capacidadBase, float capacidadActual, int dinero, float saludActual, float saludMaxima,
                       Long tiempoAcumulado, boolean tieneGuardias) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.velocidadBase = velocidadBase;
        this.velocidadActual = velocidadActual;
        this.capacidadBase = capacidadBase;
        this.capacidadActual = capacidadActual;
        this.dinero = dinero;
        this.saludActual = saludActual;
        this.saludMaxima = saludMaxima;
        this.tiempoAcumulado = tiempoAcumulado;
        this.tieneGuardias = tieneGuardias;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getVelocidadBase() {
        return velocidadBase;
    }

    public void setVelocidadBase(float velocidadBase) {
        this.velocidadBase = velocidadBase;
    }

    public float getVelocidadActual() {
        return velocidadActual;
    }

    public void setVelocidadActual(float velocidadActual) {
        this.velocidadActual = velocidadActual;
    }

    public float getCapacidadBase() {
        return capacidadBase;
    }

    public void setCapacidadBase(float capacidadBase) {
        this.capacidadBase = capacidadBase;
    }

    public float getCapacidadActual() {
        return capacidadActual;
    }

    public void setCapacidadActual(float capacidadActual) {
        this.capacidadActual = capacidadActual;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public float getSaludActual() {
        return saludActual;
    }

    public void setSaludActual(float saludActual) {
        this.saludActual = saludActual;
    }

    public float getSaludMaxima() {
        return saludMaxima;
    }

    public void setSaludMaxima(float saludMaxima) {
        this.saludMaxima = saludMaxima;
    }

    public Long getTiempoAcumulado() {
        return tiempoAcumulado;
    }

    public void setTiempoAcumulado(Long tiempoAcumulado) {
        this.tiempoAcumulado = tiempoAcumulado;
    }

    public boolean isTieneGuardias() {
        return tieneGuardias;
    }

    public void setTieneGuardias(boolean tieneGuardias) {
        this.tieneGuardias = tieneGuardias;
    }
}
