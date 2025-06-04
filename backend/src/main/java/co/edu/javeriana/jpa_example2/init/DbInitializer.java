package co.edu.javeriana.jpa_example2.init;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.model.*;
import co.edu.javeriana.jpa_example2.repository.*;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import jakarta.transaction.Transactional;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile({"default"})
@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
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
    @Autowired private CiudadService ciudadService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<Ruta> rutas = new ArrayList<>();
        List<Ciudad> ciudades = new ArrayList<>();
        Random random = new Random();

        // Crear 100 ciudades
        for (int i = 0; i < 100; i++) {
            Ciudad ciudad = ciudadRepository.save(new Ciudad("Ciudad_" + i, 100 + i * 10));
            ciudades.add(ciudad);
        }

        // Crear 100 rutas aleatorias
        for (int i = 0; i < 100; i++) {
            boolean esSegura = random.nextBoolean();
            float dano = esSegura? 0:random.nextFloat() * 50;
            float distancia = esSegura 
                ? 80 + random.nextFloat() * 70    // 80 a 150
                : 20 + random.nextFloat() * 50;   // 20 a 70
            Ciudad origen = ciudades.get(random.nextInt(ciudades.size()));
            Ciudad destino = ciudades.get(random.nextInt(ciudades.size()));
            while (origen.equals(destino) || destino.getRutasDestino().size() >= 5) {
                destino = ciudades.get(random.nextInt(ciudades.size()));
            }
            Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano, origen, destino));
            rutas.add(ruta);
        }

        // Crear 200 rutas aleatorias mas
        for (int i = 0; i < 200; i++) {
            boolean esSegura = random.nextBoolean();
            float dano = esSegura? 0:random.nextFloat() * 50;
            float distancia = esSegura 
                ? 80 + random.nextFloat() * 70    // 80 a 150
                : 20 + random.nextFloat() * 50;   // 20 a 70
            Ciudad origen = ciudades.get(random.nextInt(ciudades.size()));
            while (origen.getRutasDestino().size() >= 5) {
                origen = ciudades.get(random.nextInt(ciudades.size()));
            }
            Ciudad destino = ciudades.get(random.nextInt(ciudades.size()));
            while (origen.equals(destino) || destino.getRutasDestino().size() >= 5) {
                destino = ciudades.get(random.nextInt(ciudades.size()));
            }
            Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano, origen, destino));
            rutas.add(ruta);
        }

        // Crear productos
        List<Producto> productos = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Producto producto = productoRepository.save(new Producto("producto_" + i));
            productos.add(producto);
        }

        // Crear ProductoCiudad (relación entre productos y ciudades)
        for (Ciudad ciudad : ciudades.subList(0, 10)) {
            for (Producto producto : productos.subList(0, 10)) {
                ProductoCiudad pc = new ProductoCiudad(
                    10 + random.nextFloat() * 5,  // FD
                    5 + random.nextFloat() * 5,   // FO
                    2 + random.nextFloat() * 10,  // Stock
                    producto,
                    ciudad
                );
                productoCiudadRepository.save(pc);
            }
        }

        // Crear partidas
        List<Partida> partidas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Partida partida = partidaRepository.save(new Partida(3600L, 300));
            partidas.add(partida);
        }

        // Crear caravanas
        List<Caravana> caravanas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Caravana caravana = new Caravana("caravana_" + i, 10 + i, 100 + i * 10, 1000 + i * 100, 100 + i * 10, ciudades.get(random.nextInt(ciudades.size())));
            caravana.setPartida(partidas.get(0));
            caravanaRepository.save(caravana);
            caravanas.add(caravana);
        }


        // Crear usuarios
        userRepository.save(
                new User("Alice", "Alisson", "alice@alice.com", passwordEncoder.encode("alice123"), Role.COMERCIANTE));
        userRepository.save(
                new User("Bob", "Bobson", "bob@bob.com", passwordEncoder.encode("bob123"), Role.CARAVANERO));
        userRepository.save(
                new User("Charlie", "Charlson", "charlie@charlie.com", passwordEncoder.encode("charlie123"), Role.ADMIN));

        // Crear jugadores
        List<Jugador> jugadores = new ArrayList<>();
        List<Integer> idsCaravanas = new ArrayList<>();
        for (int j = 0; j < caravanas.size(); j++) idsCaravanas.add(j);
        Collections.shuffle(idsCaravanas);
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                Jugador jugador = new Jugador("Alice", "alice@alice.com", caravanas.get(idsCaravanas.get(i)));
                jugadorRepository.save(jugador);
                jugadores.add(jugador);
            }
            else if (i == 1){
                Jugador jugador = new Jugador("Bob", "bob@bob.com", caravanas.get(idsCaravanas.get(i)));
                jugadorRepository.save(jugador);
                jugadores.add(jugador);
            } 
            else {
                Jugador jugador = new Jugador("Charlie", "charlie@charlie.com", caravanas.get(idsCaravanas.get(i)));
                jugadorRepository.save(jugador);
                jugadores.add(jugador);
            } 
        }
            
        // --- Creación de Servicios (Específicos del Juego)
        log.info("Creando servicios globales...");
        List<Servicio> servicios = new ArrayList<>(); // Inicializa la lista

        // Añade los servicios definidos en las reglas del juego
        servicios.add(servicioRepository.save(new Servicio("Reparar Caravana", "Recupera puntos de vida de la caravana.")));
        servicios.add(servicioRepository.save(new Servicio("Ampliar Bodega", "Aumenta la capacidad máxima de carga (hasta +400% original).")));
        servicios.add(servicioRepository.save(new Servicio("Mejorar Ejes", "Aumenta la velocidad máxima de la caravana (hasta +50% original).")));
        servicios.add(servicioRepository.save(new Servicio("Contratar Guardias", "Reduce daño en rutas inseguras en 25% (compra única).")));

        // --- Asociar Productos y Servicios a Ciudades ---
        log.info("Asociando productos y servicios a ciudades...");
        int minProductosPorCiudad = 5;
        int maxProductosPorCiudad = 15;

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
            // Define el rango basado en los servicios que REALMENTE existen
            int minServiciosPorCiudad = 1;
            int maxServiciosPorCiudad = servicios.size(); // Usa el tamaño real de la lista de servicios
            // Asegura que min no sea mayor que max si solo hay 1 servicio o ninguno
            if (minServiciosPorCiudad > maxServiciosPorCiudad) {
                minServiciosPorCiudad = maxServiciosPorCiudad;
            }

            int numServiciosEnCiudad = 0;
            if (maxServiciosPorCiudad > 0) { // Solo asigna si hay servicios definidos
                 // Calcula el número aleatorio de servicios para esta ciudad (entre min y max)
                 numServiciosEnCiudad = minServiciosPorCiudad + random.nextInt(maxServiciosPorCiudad - minServiciosPorCiudad + 1);
            }

            Set<Servicio> serviciosAsignados = new HashSet<>();
            List<Servicio> serviciosMezclados = new ArrayList<>(servicios);
            Collections.shuffle(serviciosMezclados);

            // Asigna el número calculado de servicios distintos a la ciudad
            for (int i = 0; i < numServiciosEnCiudad && i < serviciosMezclados.size(); i++) {
                 Servicio servicio = serviciosMezclados.get(i);
                 if (serviciosAsignados.add(servicio)) {
                    int precio = 50 + random.nextInt(951); // Precio aleatorio
                    ServicioCiudad servicioCiudad = new ServicioCiudad(precio, servicio, ciudad);
                    servicioCiudadRepository.save(servicioCiudad);
                 }
            }
        }
    }
}