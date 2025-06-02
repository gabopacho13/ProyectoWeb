package co.edu.javeriana.jpa_example2.controller;

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

import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.model.Partida;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class PartidaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private WebTestClient webTestClient;

    public PartidaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear partidas de prueba
        Partida p1 = new Partida();
        p1.setTiempoLimite(7200L); // 2 horas en segundos
        p1.setGananciaMinima(5000.0);
        p1.setTiempoInicio(LocalDateTime.now().minusHours(1));
        p1.setTiempoActual(3600L); // 1 hora transcurrida
        partidaRepository.save(p1);

        Partida p2 = new Partida();
        p2.setTiempoLimite(10800L); // 3 horas en segundos
        p2.setGananciaMinima(8000.0);
        p2.setTiempoInicio(LocalDateTime.now().minusMinutes(30));
        p2.setTiempoActual(1800L); // 30 minutos transcurridos
        partidaRepository.save(p2);

        Partida p3 = new Partida();
        p3.setTiempoLimite(14400L); // 4 horas en segundos
        p3.setGananciaMinima(12000.0);
        p3.setTiempoInicio(LocalDateTime.now());
        p3.setTiempoActual(0L); // Recién iniciada
        partidaRepository.save(p3);
    }

    @Test
    void crearPartidaYValidarRespuesta() {
        PartidaDTO nuevaPartida = new PartidaDTO();
        nuevaPartida.setTiempoLimite(9000L); // 2.5 horas en segundos
        nuevaPartida.setGananciaMinima(6500.0);
        nuevaPartida.setTiempoInicio(LocalDateTime.now());
        nuevaPartida.setTiempoActual(0L);

        webTestClient.post()
                .uri(SERVER_URL + "partida")
                .bodyValue(nuevaPartida)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PartidaDTO.class)
                .value(partida -> {
                    assert partida != null;
                    assert partida.getId() != null;
                    assert partida.getTiempoLimite().equals(9000L);
                    assert partida.getGananciaMinima() == 6500.0;
                    assert partida.getTiempoInicio() != null;
                    assert partida.getTiempoActual().equals(0L);
                });
    }
}