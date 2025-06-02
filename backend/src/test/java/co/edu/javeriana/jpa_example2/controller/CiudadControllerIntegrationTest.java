package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

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

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CiudadControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    public CiudadControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        Ciudad c1 = new Ciudad();
        c1.setNombre("Bogotá");
        c1.setImpuesto_entrada(150);
        ciudadRepository.save(c1);

        Ciudad c2 = new Ciudad();
        c2.setNombre("Medellín");
        c2.setImpuesto_entrada(120);
        ciudadRepository.save(c2);

        Ciudad c3 = new Ciudad();
        c3.setNombre("Cali");
        c3.setImpuesto_entrada(100);
        ciudadRepository.save(c3);
    }

    // Test GET /ciudad/lista
    @Test
    void listarCiudades() {
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CiudadDTO.class)
                .hasSize(3);
    }

    // Test GET /ciudad/{id}
    @Test
    void listarCiudadPorId() {
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadDTO.class)
                .value(ciudad -> {
                    assert ciudad != null;
                    assert ciudad.getNombre().equals("Medellín");
                    assert ciudad.getImpuesto_entrada() == 120;
                });
    }

    // Test POST /ciudad
    @Test
    void crearCiudad() {
        CiudadDTO nuevaCiudad = new CiudadDTO();
        nuevaCiudad.setNombre("Cartagena");
        nuevaCiudad.setImpuesto_entrada(180);

        webTestClient.post()
                .uri(SERVER_URL + "ciudad")
                .bodyValue(nuevaCiudad)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadDTO.class)
                .value(ciudad -> {
                    assert ciudad != null;
                    assert ciudad.getNombre().equals("Cartagena");
                    assert ciudad.getImpuesto_entrada() == 180;
                    assert ciudad.getId() != null;
                });
    }

    // Test PUT /ciudad
    @Test
    void editarCiudad() {
        CiudadDTO ciudadEditada = new CiudadDTO();
        ciudadEditada.setId(1L);
        ciudadEditada.setNombre("Bogotá D.C.");
        ciudadEditada.setImpuesto_entrada(200);

        webTestClient.put()
                .uri(SERVER_URL + "ciudad")
                .bodyValue(ciudadEditada)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadDTO.class)
                .value(ciudad -> {
                    assert ciudad != null;
                    assert ciudad.getNombre().equals("Bogotá D.C.");
                    assert ciudad.getImpuesto_entrada() == 200;
                    assert ciudad.getId() == 1L;
                });
    }

    // Test DELETE /ciudad/{id}
    @Test
    void borrarCiudad() {
        webTestClient.delete()
                .uri(SERVER_URL + "ciudad/3")
                .exchange()
                .expectStatus().isOk();

        // Verificar que la ciudad fue eliminada
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CiudadDTO.class)
                .hasSize(2);
    }
}