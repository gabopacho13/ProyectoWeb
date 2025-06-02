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

import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class RutaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private RutaRepository rutaRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    public RutaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear ciudades
        Ciudad origen = new Ciudad("Ciudad-Origen", 10);
        Ciudad destino = new Ciudad("Ciudad-Destino", 15);
        ciudadRepository.save(origen);
        ciudadRepository.save(destino);

        // Crear rutas
        Ruta r1 = new Ruta(150.5f, true, 0.0f, origen, destino);
        Ruta r2 = new Ruta(200.0f, false, 25.5f, destino, origen);
        Ruta r3 = new Ruta(300.0f, true, 10.0f, origen, destino);
        
        rutaRepository.save(r1);
        rutaRepository.save(r2);
        rutaRepository.save(r3);
    }

    @Test
    void obtenerRutaPorIdDevuelveRutaEsperada() {
        webTestClient.get()
                .uri(SERVER_URL + "ruta/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(RutaDTO.class)
                .value(ruta -> {
                    assert ruta != null;
                    assert ruta.getId().equals(2L);
                    assert ruta.getDistancia() == 200.0f;
                    assert !ruta.getEs_segura(); // Ruta no segura
                    assert ruta.getDano() == 25.5f;
                });
    }
}