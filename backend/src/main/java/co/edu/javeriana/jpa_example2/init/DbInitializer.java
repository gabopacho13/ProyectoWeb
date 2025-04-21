package co.edu.javeriana.jpa_example2.init;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import co.edu.javeriana.jpa_example2.model.*;
import co.edu.javeriana.jpa_example2.repository.*;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import jakarta.transaction.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

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
            float distancia = 5 + random.nextFloat() * 95;
            boolean esSegura = random.nextBoolean();
            float dano = esSegura? 0:random.nextFloat() * 50;
            Ciudad origen = ciudades.get(i);
            Ciudad destino = ciudades.get(random.nextInt(ciudades.size()));
            while (origen.equals(destino) || destino.getRutasDestino().size() >= 5) {
                destino = ciudades.get(random.nextInt(ciudades.size()));
            }
            Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano, origen, destino));
            rutas.add(ruta);
        }
        
        // Crear 200 rutas aleatorias mas
        for (int i = 0; i < 200; i++) {
            float distancia = 5 + random.nextFloat() * 95;
            boolean esSegura = random.nextBoolean();
            float dano = esSegura? 0:random.nextFloat() * 50;
            Ciudad origen = ciudades.get(random.nextInt(ciudades.size()));
            while (origen.getRutasOrigen().size() >= 5) {
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

        // Crear servicios
        List<Servicio> servicios = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Servicio servicio = servicioRepository.save(new Servicio("servicio_" + i, "efecto_" + i));
            servicios.add(servicio);
        }

        // Crear caravanas
        List<Caravana> caravanas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Caravana caravana = new Caravana("caravana_" + i, 10 + i, 100 + i * 10, 1000 + i * 100, 100 + i * 10, ciudades.get(random.nextInt(ciudades.size())));
            caravana.setPartida(partidas.get(0));
            caravanaRepository.save(caravana);
            caravanas.add(caravana);
        }

        // Crear jugadores
        List<Jugador> jugadores = new ArrayList<>();
        List<Integer> idsCaravanas = new ArrayList<>();
        for (int j = 0; j < caravanas.size(); j++) idsCaravanas.add(j);
        Collections.shuffle(idsCaravanas);
        for (int i = 0; i < 10; i++) {
            Jugador jugador = jugadorRepository.save(new Jugador("jugador_" + i, "rol_" + i, caravanas.get(idsCaravanas.get(i))));
            jugadores.add(jugador);
        }
    }
}