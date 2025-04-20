package co.edu.javeriana.jpa_example2.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.model.*;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionProductoRepository;
import jakarta.transaction.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

    // --- Repositorios Autowired (sin cambios) ---
    @Autowired private CaravanaRepository caravanaRepository;
    @Autowired private CiudadRepository ciudadRepository;
    @Autowired private InventarioCaravanaRepository inventarioCaravanaRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private PartidaRepository partidaRepository;
    @Autowired private ProductoCiudadRepository productoCiudadRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private RutaRepository rutaRepository;
    @Autowired private ServicioCiudadRepository servicioCiudadRepository;
    @Autowired private ServicioRepository servicioRepository;
    @Autowired private ServiciosCompradosRepository serviciosCompradosRepository;
    @Autowired private TransaccionProductoRepository transaccionRepository;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<Ruta> rutas = new ArrayList<>();
        List<Ciudad> ciudades = new ArrayList<>();
        Random random = new Random();

        // --- Creación de Ciudades (CON IMPUESTO) ---
        log.info("Creando ciudades...");
        for (int i = 0; i < 100; i++) {
            // *** LÍNEA MODIFICADA: Reincorporado el impuesto ***
            int impuestoCiudad = 100 + i * 10; // Calcula el impuesto
            Ciudad ciudad = ciudadRepository.save(new Ciudad("Ciudad_" + i, impuestoCiudad)); // Pasa el impuesto al constructor
            ciudades.add(ciudad);
        }
        log.info("{} ciudades creadas (con impuestos).", ciudades.size());

        // --- Creación de Rutas (sin cambios respecto a la versión anterior) ---
        log.info("Creando rutas...");
        // (Misma lógica de creación de rutas que antes...)
        for (int i = 0; i < 100; i++) {
             float distancia = 5 + random.nextFloat() * 95;
             boolean esSegura = random.nextBoolean();
             float dano = esSegura? 0: 1 + random.nextFloat() * 49;
             Ciudad origen = ciudades.get(i);
             Ciudad destino = ciudades.get(random.nextInt(ciudades.size()));
             int intentos = 0;
             while (origen.equals(destino) || destino.getRutasDestino().size() >= 5) {
                 destino = ciudades.get(random.nextInt(ciudades.size()));
                 intentos++;
                 if (intentos > ciudades.size()) break;
             }
             if (!origen.equals(destino)) {
                 Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano, origen, destino));
                 rutas.add(ruta);
             }
        }
        int rutasAdicionales = 200;
        for (int i = 0; i < rutasAdicionales; i++) {
             float distancia = 5 + random.nextFloat() * 95;
             boolean esSegura = random.nextBoolean();
             float dano = esSegura? 0: 1 + random.nextFloat() * 49;
             Ciudad origen = ciudades.get(random.nextInt(ciudades.size()));
             Ciudad destino = ciudades.get(random.nextInt(ciudades.size()));
             int intentos = 0;
             while (origen.equals(destino) || origen.getRutasOrigen().size() >= 5 || destino.getRutasDestino().size() >= 5) {
                  origen = ciudades.get(random.nextInt(ciudades.size()));
                  destino = ciudades.get(random.nextInt(ciudades.size()));
                  intentos++;
                  if (intentos > ciudades.size() * 2) break;
             }
             if (!origen.equals(destino)) {
                Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano, origen, destino));
                rutas.add(ruta);
             }
        }
        log.info("{} rutas creadas en total.", rutas.size());


        // --- Creación de Productos (sin cambios) ---
        log.info("Creando productos globales...");
        List<Producto> productos = new ArrayList<>();
        int numProductosGlobales = 50;
        for (int i = 0; i < numProductosGlobales; i++) {
            Producto producto = productoRepository.save(new Producto("Producto_" + i));
            productos.add(producto);
        }
        log.info("{} productos globales creados.", productos.size());

        // --- Creación de Servicios (sin cambios) ---
        log.info("Creando servicios globales...");
        List<Servicio> servicios = new ArrayList<>();
        servicios.add(servicioRepository.save(new Servicio("Reparar", "Recupera HP")));
        servicios.add(servicioRepository.save(new Servicio("Mejorar Capacidad", "Aumenta capacidad max carga")));
        servicios.add(servicioRepository.save(new Servicio("Mejorar Velocidad", "Aumenta velocidad max")));
        servicios.add(servicioRepository.save(new Servicio("Contratar Guardias", "Reduce daño en rutas inseguras")));
        log.info("{} servicios globales creados.", servicios.size());


        // --- Asociar Productos y Servicios a Ciudades (sin cambios respecto a la versión anterior) ---
        log.info("Asociando productos y servicios a ciudades...");
        int minProductosPorCiudad = 5;
        int maxProductosPorCiudad = 15;
        int minServiciosPorCiudad = 1;
        int maxServiciosPorCiudad = servicios.size();

        for (Ciudad ciudad : ciudades) {
            // --- Asociar Productos ---
            int numProductosEnCiudad = minProductosPorCiudad + random.nextInt(maxProductosPorCiudad - minProductosPorCiudad + 1);
            Set<Producto> productosAsignados = new HashSet<>();
            List<Producto> productosMezclados = new ArrayList<>(productos);
            Collections.shuffle(productosMezclados);

            for (int i = 0; i < numProductosEnCiudad && i < productosMezclados.size(); i++) {
                 Producto producto = productosMezclados.get(i);
                 if (productosAsignados.add(producto)) {
                     float factorDemanda = 0.5f + random.nextFloat() * 1.5f;
                     float factorOferta = 0.5f + random.nextFloat() * 1.5f;
                     float stock = 10 + random.nextInt(491);

                     ProductoCiudad productoCiudad = new ProductoCiudad(factorDemanda, factorOferta, stock, producto, ciudad);
                     productoCiudadRepository.save(productoCiudad);
                 }
            }

            // --- Asociar Servicios ---
            int numServiciosEnCiudad = minServiciosPorCiudad + random.nextInt(maxServiciosPorCiudad - minServiciosPorCiudad + 1);
            Set<Servicio> serviciosAsignados = new HashSet<>();
            List<Servicio> serviciosMezclados = new ArrayList<>(servicios);
            Collections.shuffle(serviciosMezclados);

            for (int i = 0; i < numServiciosEnCiudad && i < serviciosMezclados.size(); i++) {
                Servicio servicio = serviciosMezclados.get(i);
                 if (serviciosAsignados.add(servicio)) {
                    int precio = 50 + random.nextInt(951);

                    ServicioCiudad servicioCiudad = new ServicioCiudad(precio, servicio, ciudad);
                    servicioCiudadRepository.save(servicioCiudad);
                 }
            }
        }
        log.info("Asociación de productos y servicios a ciudades completada.");


        // --- Creación de Partidas (sin cambios) ---
        log.info("Creando partidas...");
        List<Partida> partidas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Partida partida = partidaRepository.save(new Partida(3600L, 300));
            partidas.add(partida);
        }
        log.info("{} partidas creadas.", partidas.size());

        // --- Creación de Caravanas (sin cambios) ---
        log.info("Creando caravanas...");
        List<Caravana> caravanas = new ArrayList<>();
        if (!ciudades.isEmpty()) {
             for (int i = 0; i < 10; i++) {
                 Ciudad ciudadInicio = ciudades.get(random.nextInt(ciudades.size()));
                 Caravana caravana = new Caravana("Caravana_" + i, 10 + i, 100 + i * 10, 1000 + i * 100, 100 + i * 10, ciudadInicio);
                 caravana.setPartida(partidas.get(0));
                 caravanaRepository.save(caravana);
                 caravanas.add(caravana);
             }
             log.info("{} caravanas creadas.", caravanas.size());
        } else {
            log.warn("No se pudieron crear caravanas porque no hay ciudades.");
        }


        // --- Creación de Jugadores (sin cambios) ---
        log.info("Creando jugadores...");
        List<Jugador> jugadores = new ArrayList<>();
        List<Integer> idsCaravanas = new ArrayList<>();
        if (!caravanas.isEmpty()){
            for (int j = 0; j < caravanas.size(); j++) {
                idsCaravanas.add(j);
            }
            Collections.shuffle(idsCaravanas);
            for (int i = 0; i < 10; i++) {
                if (i < idsCaravanas.size()) {
                     Jugador jugador = jugadorRepository.save(new Jugador("Jugador_" + i , "Comerciante", caravanas.get(idsCaravanas.get(i))));
                     jugadores.add(jugador);
                }
            }
             log.info("{} jugadores creados y asignados a caravanas.", jugadores.size());
        } else {
            log.warn("No se pudieron crear jugadores porque no hay caravanas.");
        }

        log.info("----- INICIALIZACIÓN DE BASE DE DATOS COMPLETADA -----");
    }
}