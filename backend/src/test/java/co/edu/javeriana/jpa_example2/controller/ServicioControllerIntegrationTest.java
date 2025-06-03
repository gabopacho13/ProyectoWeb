package co.edu.javeriana.jpa_example2.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
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
import co.edu.javeriana.jpa_example2.dto.ServicioDTO;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServicioControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public ServicioControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        Servicio s1 = new Servicio("Reparación", "Restaura salud");
        Servicio s2 = new Servicio("Comercio", "Permite intercambio de objetos");
        servicioRepository.save(s1);
        servicioRepository.save(s2);
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
    void crearServicio() {
        ServicioDTO nuevo = new ServicioDTO(null, "Transporte", "Aumenta velocidad de caravana");

        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Admin should have access
        webTestClient.post()
            .uri(SERVER_URL + "servicio/crear")
            .headers(headers -> headers.setBearerAuth(adminToken))
            .bodyValue(nuevo)
            .exchange()
            .expectStatus().isOk()
            .expectBody(ServicioDTO.class)
            .value(servicio -> {
                assert servicio.getId() != null;
                assert servicio.getTipo().equals("Transporte");
                assert servicio.getEfecto().equals("Aumenta velocidad de caravana");
            });

        // Comerciante should not have access
        webTestClient.post()
            .uri(SERVER_URL + "servicio/crear")
            .headers(headers -> headers.setBearerAuth(comercianteToken))
            .bodyValue(nuevo)
            .exchange()
            .expectStatus().isForbidden();

        // Caravanero should not have access
        webTestClient.post()
            .uri(SERVER_URL + "servicio/crear")
            .headers(headers -> headers.setBearerAuth(caravaneroToken))
            .bodyValue(nuevo)
            .exchange()
            .expectStatus().isForbidden();
    }
}
