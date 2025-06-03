package co.edu.javeriana.jpa_example2.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;

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
import co.edu.javeriana.jpa_example2.dto.PartidaCaravanasDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Partida;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.PartidaRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class PartidaCaravanaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public PartidaCaravanaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        // Crear partida de prueba
        Partida p1 = new Partida();
        p1.setTiempoLimite(7200L);
        p1.setGananciaMinima(5000.0);
        p1.setTiempoInicio(LocalDateTime.now());
        p1.setTiempoActual(0L);
        partidaRepository.save(p1);

        // Crear caravanas de prueba
        Caravana c1 = new Caravana();
        c1.setFecha_creacion(LocalDateTime.now());
        c1.setNombre("caravana-test-1");
        c1.setVelocidad_base(10.5f);
        c1.setVelocidad_actual(9.8f);
        c1.setCapacidad_base(100.0f);
        c1.setCapacidad_actual(85.0f);
        c1.setDinero(1500);
        c1.setSalud_actual(80.0f);
        c1.setSalud_maxima(100.0f);
        c1.setTiempo_acumulado(3600L);
        c1.setTiene_guardias(true);
        c1.setPartida(p1);
        caravanaRepository.save(c1);

        Caravana c2 = new Caravana();
        c2.setFecha_creacion(LocalDateTime.now());
        c2.setNombre("caravana-test-2");
        c2.setVelocidad_base(8.0f);
        c2.setVelocidad_actual(8.0f);
        c2.setCapacidad_base(120.0f);
        c2.setCapacidad_actual(120.0f);
        c2.setDinero(2000);
        c2.setSalud_actual(95.0f);
        c2.setSalud_maxima(100.0f);
        c2.setTiempo_acumulado(2700L);
        c2.setTiene_guardias(false);
        c2.setPartida(p1);
        caravanaRepository.save(c2);

        // Crear caravanas sin partida para el test de POST
        Caravana c3 = new Caravana();
        c3.setFecha_creacion(LocalDateTime.now());
        c3.setNombre("caravana-libre-1");
        c3.setVelocidad_base(9.0f);
        c3.setVelocidad_actual(9.0f);
        c3.setCapacidad_base(110.0f);
        c3.setCapacidad_actual(110.0f);
        c3.setDinero(1800);
        c3.setSalud_actual(90.0f);
        c3.setSalud_maxima(100.0f);
        c3.setTiempo_acumulado(0L);
        c3.setTiene_guardias(true);
        caravanaRepository.save(c3);

        Caravana c4 = new Caravana();
        c4.setFecha_creacion(LocalDateTime.now());
        c4.setNombre("caravana-libre-2");
        c4.setVelocidad_base(7.5f);
        c4.setVelocidad_actual(7.5f);
        c4.setCapacidad_base(95.0f);
        c4.setCapacidad_actual(95.0f);
        c4.setDinero(1200);
        c4.setSalud_actual(85.0f);
        c4.setSalud_maxima(100.0f);
        c4.setTiempo_acumulado(0L);
        c4.setTiene_guardias(false);
        caravanaRepository.save(c4);
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
    void listarCaravanasPorPartidaYValidarRespuesta() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();

        webTestClient.get()
                .uri(SERVER_URL + "partida/caravana/1")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PartidaCaravanasDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getIdPartida().equals(1L);
                    assert resultado.getCaravanasIds() != null;
                    assert resultado.getCaravanasIds().size() == 2;
                    assert resultado.getCaravanasIds().contains(1L);
                    assert resultado.getCaravanasIds().contains(2L);
                });

        // Attempt access with non-admin tokens
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        webTestClient.get()
                .uri(SERVER_URL + "partida/caravana/1")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();

        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        webTestClient.get()
                .uri(SERVER_URL + "partida/caravana/1")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isForbidden();
    }
}