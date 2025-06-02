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

import co.edu.javeriana.jpa_example2.dto.CiudadRutasDestinoDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CiudadRutasDestinoControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private WebTestClient webTestClient;

    public CiudadRutasDestinoControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear ciudades
        Ciudad bogota = new Ciudad();
        bogota.setNombre("Bogotá");
        bogota.setImpuesto_entrada(150);
        ciudadRepository.save(bogota);

        Ciudad medellin = new Ciudad();
        medellin.setNombre("Medellín");
        medellin.setImpuesto_entrada(120);
        ciudadRepository.save(medellin);

        Ciudad cali = new Ciudad();
        cali.setNombre("Cali");
        cali.setImpuesto_entrada(100);
        ciudadRepository.save(cali);

        // Crear rutas con Bogotá como destino
        Ruta ruta1 = new Ruta();
        ruta1.setDistancia(240.5f);
        ruta1.setEs_segura(true);
        ruta1.setDano(5.0f);
        ruta1.setOrigen(medellin);
        ruta1.setDestino(bogota);
        rutaRepository.save(ruta1);

        Ruta ruta2 = new Ruta();
        ruta2.setDistancia(456.8f);
        ruta2.setEs_segura(false);
        ruta2.setDano(15.2f);
        ruta2.setOrigen(cali);
        ruta2.setDestino(bogota);
        rutaRepository.save(ruta2);
    }

    @Test
    void getCiudadRutasDestino() {
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/destino/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadRutasDestinoDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getCiudadId().equals(1L);
                    assert resultado.getRutasDestinoIds().size() == 2;
                    // Verificar que contiene los IDs de las rutas creadas
                    assert resultado.getRutasDestinoIds().contains(1L);
                    assert resultado.getRutasDestinoIds().contains(2L);
                });
    }
}