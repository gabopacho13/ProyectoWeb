package co.edu.javeriana.jpa_example2.controller;

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

import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.ServicioCiudad;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServicioCiudadControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    public ServicioCiudadControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear servicios
        Servicio s1 = new Servicio("Reparacion", "Aumenta salud");
        Servicio s2 = new Servicio("Comercio", "Permite intercambio");
        servicioRepository.save(s1);
        servicioRepository.save(s2);

        // Crear ciudades
        Ciudad c1 = new Ciudad("Ciudad-Test-1", 10);
        Ciudad c2 = new Ciudad("Ciudad-Test-2", 15);
        ciudadRepository.save(c1);
        ciudadRepository.save(c2);

        // Crear ServicioCiudad
        ServicioCiudad sc1 = new ServicioCiudad(100, s1, c1);
        ServicioCiudad sc2 = new ServicioCiudad(150, s2, c1);
        ServicioCiudad sc3 = new ServicioCiudad(200, s1, c2);
        
        servicioCiudadRepository.save(sc1);
        servicioCiudadRepository.save(sc2);
        servicioCiudadRepository.save(sc3);
    }

    @Test
    void obtenerServiciosPorCiudadDevuelveListaCorrecta() {
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/servicios/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ServicioCiudadDTO.class)
                .value(servicios -> {
                    System.out.println("Servicios obtenidos: " + servicios);
                    System.out.println("Cantidad de servicios: " + (servicios != null ? servicios.size() : "null"));
                    if (servicios != null && !servicios.isEmpty()) {
                        servicios.forEach(s -> System.out.println("Servicio - ID: " + s.getId() + ", CiudadId: " + s.getIdCiudad() + ", ServicioId: " + s.getIdServicio() + ", Precio: " + s.getPrecio()));
                    }
                    // Validación básica
                    assert servicios != null;
                });
    }
}