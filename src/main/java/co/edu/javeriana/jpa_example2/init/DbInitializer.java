package co.edu.javeriana.jpa_example2.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import co.edu.javeriana.jpa_example2.repository.TransaccionRepository;
import jakarta.transaction.Transactional;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ProductoCiudadRepository productoCiudadRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<Ruta> rutas = new ArrayList<>();
        List<Ciudad> ciudades = new ArrayList<>();
        Random random = new Random();

        // Crear 10 rutas aleatorias
        for (int i = 0; i < 10; i++) {
            float distancia = 5 + random.nextFloat() * 95; // Distancia entre 5 y 100 km
            boolean esSegura = random.nextBoolean(); // Aleatorio entre true o false
            float dano = random.nextFloat() * 50; // Daño entre 0 y 50%
            Ruta ruta = rutaRepository.save(new Ruta(distancia, esSegura, dano));
            rutas.add(ruta);
        }

        // Crear 100 ciudades y asignarles rutas
        for (int i = 0; i < 100; i++) {
            List<Ruta> rutasAsignadas = new ArrayList<>();

            // Solo la mitad de las ciudades tendrán rutas asignadas
            if (i % 2 == 0) {
                int numRutas = 1 + random.nextInt(5); // Cada ciudad tendrá entre 1 y 5 rutas
                for (int j = 0; j < numRutas; j++) {
                    rutasAsignadas.add(rutas.get(random.nextInt(rutas.size()))); // Selección aleatoria de rutas
                }
            }

            Ciudad ciudad = ciudadRepository.save(new Ciudad("Ciudad_" + i, 100 + i * 10, rutasAsignadas));
            ciudades.add(ciudad);
        }

        // Lista para almacenar los productos
        List<Producto> productos = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Producto producto = productoRepository.save(new Producto("producto_" + i)); 
            productos.add(producto);
        }

        // Lista para almacenar los servicios
        List<Servicio> servicios = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Servicio servicio = servicioRepository.save(new Servicio("servicio_" + i, "efecto_" + i)); 
            servicios.add(servicio);
        }

        // Lista para almacenar los jugadores
        List<Jugador> jugadores = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Jugador jugador = jugadorRepository.save(new Jugador("jugador_" + i , "rol_" + i)); 
            jugadores.add(jugador);
        }
    }

}
