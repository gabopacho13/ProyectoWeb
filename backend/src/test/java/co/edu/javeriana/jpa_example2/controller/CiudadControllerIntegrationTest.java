package co.edu.javeriana.jpa_example2.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CiudadControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public CiudadControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

    private JwtAuthenticationResponse login(String email, String password) {

		RequestEntity<LoginDTO> request = RequestEntity.post(SERVER_URL + "/auth/login")
				.body(new LoginDTO(email, password));
		ResponseEntity<JwtAuthenticationResponse> jwtResponse = rest.exchange(request, JwtAuthenticationResponse.class);
		JwtAuthenticationResponse body = jwtResponse.getBody();
		assertNotNull(body);
		return body;
	}

    // Test GET /ciudad/lista
    @Test
    void listarCiudades() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();

        // Admin can access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CiudadDTO.class)
                .hasSize(3);

        // Caravanero can access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CiudadDTO.class)
                .hasSize(3);

        // Comerciante is forbidden
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();
    }

    // Test GET /ciudad/{id}
    @Test
    void listarCiudadPorId() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();

        // Admin can access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/2")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadDTO.class)
                .value(ciudad -> {
                    assert ciudad != null;
                    assert ciudad.getNombre().equals("Medellín");
                    assert ciudad.getImpuesto_entrada() == 120;
                });

        // Caravanero can access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/2")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadDTO.class)
                .value(ciudad -> {
                    assert ciudad != null;
                    assert ciudad.getNombre().equals("Medellín");
                    assert ciudad.getImpuesto_entrada() == 120;
                });

        // Comerciante is forbidden
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/2")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();
    }

    // Test POST /ciudad
    @Test
    void crearCiudad() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        CiudadDTO nuevaCiudad = new CiudadDTO();
        nuevaCiudad.setNombre("Cartagena");
        nuevaCiudad.setImpuesto_entrada(180);

        // Admin can access
        webTestClient.post()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(adminToken))
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

        // Comerciante is forbidden
        webTestClient.post()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .bodyValue(nuevaCiudad)
                .exchange()
                .expectStatus().isForbidden();

        // Caravanero is forbidden
        webTestClient.post()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .bodyValue(nuevaCiudad)
                .exchange()
                .expectStatus().isForbidden();
    }

    // Test PUT /ciudad
    @Test
    void editarCiudad() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        CiudadDTO ciudadEditada = new CiudadDTO();
        ciudadEditada.setId(1L);
        ciudadEditada.setNombre("Bogotá D.C.");
        ciudadEditada.setImpuesto_entrada(200);

        // Admin can access
        webTestClient.put()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(adminToken))
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

        // Comerciante is forbidden
        webTestClient.put()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .bodyValue(ciudadEditada)
                .exchange()
                .expectStatus().isForbidden();

        // Caravanero is forbidden
        webTestClient.put()
                .uri(SERVER_URL + "ciudad")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .bodyValue(ciudadEditada)
                .exchange()
                .expectStatus().isForbidden();
    }

    // Test DELETE /ciudad/{id}
    @Test
    void borrarCiudad() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Admin can access
        webTestClient.delete()
                .uri(SERVER_URL + "ciudad/3")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk();

        // Comerciante is forbidden
        webTestClient.delete()
                .uri(SERVER_URL + "ciudad/3")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();

        // Caravanero is forbidden
        webTestClient.delete()
                .uri(SERVER_URL + "ciudad/3")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isForbidden();

        // Verify that the city was deleted
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/lista")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CiudadDTO.class)
                .hasSize(2);
    }
}