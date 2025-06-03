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

import co.edu.javeriana.jpa_example2.dto.CaravanaJugadoresDTO;
import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Jugador;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CaravanaJugadoresControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public CaravanaJugadoresControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        // Crear caravanas de prueba
        Caravana c1 = new Caravana();
        c1.setFecha_creacion(LocalDateTime.now());
        c1.setNombre("caravana-test-1");
        c1.setVelocidad_base(10.0f);
        c1.setVelocidad_actual(9.5f);
        c1.setCapacidad_base(100.0f);
        c1.setCapacidad_actual(85.0f);
        c1.setDinero(1000);
        c1.setSalud_actual(90.0f);
        c1.setSalud_maxima(100.0f);
        c1.setTiempo_acumulado(3600L);
        c1.setTiene_guardias(true);
        c1 = caravanaRepository.save(c1);

        Caravana c2 = new Caravana();
        c2.setFecha_creacion(LocalDateTime.now());
        c2.setNombre("caravana-test-2");
        c2.setVelocidad_base(8.0f);
        c2.setVelocidad_actual(8.0f);
        c2.setCapacidad_base(120.0f);
        c2.setCapacidad_actual(120.0f);
        c2.setDinero(1500);
        c2.setSalud_actual(100.0f);
        c2.setSalud_maxima(100.0f);
        c2.setTiempo_acumulado(2400L);
        c2.setTiene_guardias(false);
        c2 = caravanaRepository.save(c2);

        // Crear jugadores de prueba
        Jugador j1 = new Jugador("Jugador-Test-1", "Lider", c1);
        jugadorRepository.save(j1);

        Jugador j2 = new Jugador("Jugador-Test-2", "Explorador", c1);
        jugadorRepository.save(j2);

        Jugador j3 = new Jugador("Jugador-Test-3", "Comerciante", c1);
        jugadorRepository.save(j3);

        Jugador j4 = new Jugador("Jugador-Test-4", "Guardian", c2);
        jugadorRepository.save(j4);

        Jugador j5 = new Jugador("Jugador-Test-5", "Medico");
        jugadorRepository.save(j5);
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
    void listarJugadoresPorCaravanaDevuelveJugadoresCorrectamente() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        webTestClient.get()
                .uri(SERVER_URL + "caravana/jugadores/1")
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CaravanaJugadoresDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getIdCaravana().equals(1L);
                    assert resultado.getJugadoresIds().size() == 3;
                    assert resultado.getJugadoresIds().contains(1L);
                    assert resultado.getJugadoresIds().contains(2L);
                    assert resultado.getJugadoresIds().contains(3L);
                });

        webTestClient.get()
                .uri(SERVER_URL + "caravana/jugadores/2")
                .header("Authorization", "Bearer " + caravaneroToken)
                .exchange()
                .expectStatus().isForbidden();

        webTestClient.get()
                .uri(SERVER_URL + "caravana/jugadores/2")
                .header("Authorization", "Bearer " + comercianteToken)
                .exchange()
                .expectStatus().isForbidden();

    }
}
