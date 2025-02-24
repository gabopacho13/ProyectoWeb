package co.edu.javeriana.jpa_example2.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Caravana {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDateTime fecha_creacion;
    private String nombre;
    private float velocidad_base;
    private float velocidad_actual;
    private float capacidad_base;
    private float capacidad_actual;
    private int dinero;
    private float salud_actual;
    private float salud_maxima;
    private Long tiempo_acumulado;
    private boolean tiene_guardias;

    @OneToMany (mappedBy = "caravana")
    private List<Jugador> jugadores = new ArrayList<>();

    @OneToMany(mappedBy = "caravana")
    private List<InventarioCaravana> inventarioCaravanas = new ArrayList<>();

    @OneToMany(mappedBy = "caravana")
    private List<ServiciosComprados> serviciosComprados = new ArrayList<>();

    @OneToMany(mappedBy = "caravana")
    private List<Transaccion> transacciones = new ArrayList<>();

    @ManyToOne
    private Ciudad ciudad_actual;

    @ManyToOne
    private Partida partida;

    public Caravana() {
        fecha_creacion = LocalDateTime.now();
        tiempo_acumulado = 0L;
        tiene_guardias = false;
    }

    public Caravana(String nombre, float velocidad_base, float capacidad_base, int dinero, float salud_maxima, Ciudad ciudad_actual) {
        this.fecha_creacion = LocalDateTime.now();
        this.nombre = nombre;
        this.velocidad_base = velocidad_base;
        this.velocidad_actual = velocidad_base;
        this.capacidad_base = capacidad_base;
        this.capacidad_actual = capacidad_base;
        this.dinero = dinero;
        this.salud_maxima = salud_maxima;
        this.salud_actual = salud_maxima;
        this.tiempo_acumulado = 0L;
        this.tiene_guardias = false;
        this.ciudad_actual = ciudad_actual;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getVelocidad_base() {
        return velocidad_base;
    }

    public void setVelocidad_base(float velocidad_base) {
        this.velocidad_base = velocidad_base;
    }

    public float getVelocidad_actual() {
        return velocidad_actual;
    }

    public void setVelocidad_actual(float velocidad_actual) {
        this.velocidad_actual = velocidad_actual;
    }

    public float getCapacidad_base() {
        return capacidad_base;
    }

    public void setCapacidad_base(float capacidad_base) {
        this.capacidad_base = capacidad_base;
    }

    public float getCapacidad_actual() {
        return capacidad_actual;
    }

    public void setCapacidad_actual(float capacidad_actual) {
        this.capacidad_actual = capacidad_actual;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public float getSalud_actual() {
        return salud_actual;
    }

    public void setSalud_actual(float salud_actual) {
        this.salud_actual = salud_actual;
    }

    public float getSalud_maxima() {
        return salud_maxima;
    }

    public void setSalud_maxima(float salud_maxima) {
        this.salud_maxima = salud_maxima;
    }

    public Long getTiempo_acumulado() {
        return tiempo_acumulado;
    }

    public void setTiempo_acumulado(Long tiempo_acumulado) {
        this.tiempo_acumulado = tiempo_acumulado;
    }

    public boolean getTiene_guardias() {
        return tiene_guardias;
    }

    public void setTiene_guardias(boolean tiene_guardias) {
        this.tiene_guardias = tiene_guardias;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
        jugador.setCaravana(this);
    }

    public void removeJugador(Jugador jugador) {
        jugadores.remove(jugador);
        jugador.setCaravana(null);
    }

    public Ciudad getCiudad_actual() {
        return ciudad_actual;
    }

    public void setCiudad_actual(Ciudad ciudad_actual) {
        this.ciudad_actual = ciudad_actual;
    }

    public List<InventarioCaravana> getInventarioCaravanas() {
        return inventarioCaravanas;
    }

    public void addInventarioCaravana(InventarioCaravana inventarioCaravana) {
        inventarioCaravanas.add(inventarioCaravana);
        inventarioCaravana.setCaravana(this);
    }

    public void removeInventarioCaravana(InventarioCaravana inventarioCaravana) {
        inventarioCaravanas.remove(inventarioCaravana);
        inventarioCaravana.setCaravana(null);
    }

    public List<ServiciosComprados> getServiciosComprados() {
        return serviciosComprados;
    }

    public void addServiciosComprados(ServiciosComprados serviciosComprados) {
        this.serviciosComprados.add(serviciosComprados);
        serviciosComprados.setCaravana(this);
    }

    public void removeServiciosComprados(ServiciosComprados serviciosComprados) {
        this.serviciosComprados.remove(serviciosComprados);
        serviciosComprados.setCaravana(null);
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void addTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
        transaccion.setCaravana(this);
    }

    public void removeTransaccion(Transaccion transaccion) {
        this.transacciones.remove(transaccion);
        transaccion.setCaravana(null);
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}
