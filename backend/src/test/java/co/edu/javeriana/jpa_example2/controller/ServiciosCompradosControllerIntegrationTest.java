package co.edu.javeriana.jpa_example2.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServiciosCompradosControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;

    @Autowired
    private WebTestClient webTestClient;

    public ServiciosCompradosControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear caravanas de prueba
        Caravana c1 = new Caravana();
        c1.setFecha_creacion(LocalDateTime.now());
        c1.setNombre("caravana-prueba-1");
        c1.setVelocidad_base(10.5f);
        c1.setVelocidad_actual(9.8f);
        c1.setCapacidad_base(100.0f);
        c1.setCapacidad_actual(85.0f);
        c1.setDinero(1500);
        c1.setSalud_actual(80.0f);
        c1.setSalud_maxima(100.0f);
        c1.setTiempo_acumulado(3600L);
        c1.setTiene_guardias(true);
        caravanaRepository.save(c1);

        Caravana c2 = new Caravana();
        c2.setFecha_creacion(LocalDateTime.now());
        c2.setNombre("caravana-prueba-2");
        c2.setVelocidad_base(8.0f);
        c2.setVelocidad_actual(8.0f);
        c2.setCapacidad_base(120.0f);
        c2.setCapacidad_actual(120.0f);
        c2.setDinero(2000);
        c2.setSalud_actual(95.0f);
        c2.setSalud_maxima(100.0f);
        c2.setTiempo_acumulado(2700L);
        c2.setTiene_guardias(false);
        caravanaRepository.save(c2);

        // Crear servicios de prueba
        Servicio s1 = new Servicio("Reparación", "Aumenta la salud de la caravana");
        servicioRepository.save(s1);

        Servicio s2 = new Servicio("Suministros", "Aumenta la capacidad actual");
        servicioRepository.save(s2);

        Servicio s3 = new Servicio("Guardias", "Mejora la seguridad de la caravana");
        servicioRepository.save(s3);
    }

    @Test
    void crearServicioCompradoDevuelveServicioConDatosCorrectos() {
        // Crear DTO para el POST
        ServiciosCompradosDTO nuevoServicioDTO = new ServiciosCompradosDTO();
        nuevoServicioDTO.setIdCaravana(1L);
        nuevoServicioDTO.setIdServicio(1L);
        nuevoServicioDTO.setFechaCompra(new Date(System.currentTimeMillis()));

        webTestClient.post()
                .uri(SERVER_URL + "caravana/servicios")
                .bodyValue(nuevoServicioDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ServiciosCompradosDTO.class)
                .value(servicioComprado -> {
                    // Validaciones del contenido del objeto devuelto
                    assert servicioComprado != null;
                    assert servicioComprado.getId() != null;
                    assert servicioComprado.getIdCaravana().equals(1L);
                    assert servicioComprado.getIdServicio().equals(1L);
                    assert servicioComprado.getFechaCompra() != null;
                });
    }
}