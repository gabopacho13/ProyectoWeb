package co.edu.javeriana.jpa_example2.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.model.Servicio;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServicioControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServicioRepository servicioRepository;

    public ServicioControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        Servicio s1 = new Servicio("Reparación", "Restaura salud");
        Servicio s2 = new Servicio("Comercio", "Permite intercambio de objetos");
        servicioRepository.save(s1);
        servicioRepository.save(s2);
    }

    @Test
    void crearServicio() {
        ServicioDTO nuevo = new ServicioDTO(null, "Transporte", "Aumenta velocidad de caravana");

        webTestClient.post()
            .uri(SERVER_URL + "servicio/crear")
            .bodyValue(nuevo)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ServicioDTO.class)
            .value(servicio -> {
                assert servicio.getId() != null;
                assert servicio.getTipo().equals("Transporte");
                assert servicio.getEfecto().equals("Aumenta velocidad de caravana");
            });
    }
}
