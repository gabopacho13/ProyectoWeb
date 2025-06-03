package co.edu.javeriana.jpa_example2.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.Ruta;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.RutaRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public RutaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

    private JwtAuthenticationResponse login(String email, String password) {

		RequestEntity<LoginDTO> request = RequestEntity.post(SERVER_URL + "/auth/login")
				.body(new LoginDTO(email, password));
		ResponseEntity<JwtAuthenticationResponse> jwtResponse = rest.exchange(request, JwtAuthenticationResponse.class);
		JwtAuthenticationResponse body = jwtResponse.getBody();
		assertNotNull(body);
		return body;
	}

    @Test
    void obtenerRutaPorIdDevuelveRutaEsperada() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();

        // Admin access
        webTestClient.get()
                .uri(SERVER_URL + "ruta/2")
                .headers(headers -> headers.setBearerAuth(adminToken))
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

        // Caravanero access
        webTestClient.get()
                .uri(SERVER_URL + "ruta/2")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
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

        // Comerciante access denied
        webTestClient.get()
                .uri(SERVER_URL + "ruta/2")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();
    }
}