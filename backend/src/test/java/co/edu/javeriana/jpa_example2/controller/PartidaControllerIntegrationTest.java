package co.edu.javeriana.jpa_example2.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.model.Partida;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class PartidaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public PartidaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {

        userRepository.save(
                new User("Alice", "Alisson", "alice@alice.com", passwordEncoder.encode("alice123"), Role.COMERCIANTE));
        userRepository.save(
                new User("Bob", "Bobson", "bob@bob.com", passwordEncoder.encode("bob123"), Role.CARAVANERO));
        userRepository.save(
                new User("Charlie", "Charlson", "charlie@charlie.com", passwordEncoder.encode("charlie123"), Role.ADMIN));

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

    private JwtAuthenticationResponse login(String email, String password) {

		RequestEntity<LoginDTO> request = RequestEntity.post(SERVER_URL + "/auth/login")
				.body(new LoginDTO(email, password));
		ResponseEntity<JwtAuthenticationResponse> jwtResponse = rest.exchange(request, JwtAuthenticationResponse.class);
		JwtAuthenticationResponse body = jwtResponse.getBody();
		assertNotNull(body);
		return body;
	}

    @Test
    void crearPartidaYValidarRespuesta() {
        PartidaDTO nuevaPartida = new PartidaDTO();
        nuevaPartida.setTiempoLimite(9000L); // 2.5 horas en segundos
        nuevaPartida.setGananciaMinima(6500.0);
        nuevaPartida.setTiempoInicio(LocalDateTime.now());
        nuevaPartida.setTiempoActual(0L);

        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Admin access
        webTestClient.post()
                .uri(SERVER_URL + "partida")
                .headers(headers -> headers.setBearerAuth(adminToken))
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

        // Caravanero access
        webTestClient.post()
                .uri(SERVER_URL + "partida")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
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

        // Comerciante access (should fail)
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        webTestClient.post()
                .uri(SERVER_URL + "partida")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .bodyValue(nuevaPartida)
                .exchange()
                .expectStatus().isForbidden();
    }
}