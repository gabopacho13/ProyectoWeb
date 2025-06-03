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
import co.edu.javeriana.jpa_example2.dto.ServicioCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.ServicioCiudad;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioCiudadRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServicioCiudadControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private ServicioCiudadRepository servicioCiudadRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
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

    public ServicioCiudadControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        // Crear servicios
        Servicio s1 = new Servicio("Reparacion", "Aumenta salud");
        Servicio s2 = new Servicio("Comercio", "Permite intercambio");
        servicioRepository.save(s1);
        servicioRepository.save(s2);

        // Crear ciudades
        Ciudad c1 = new Ciudad("Ciudad-Test-1", 10);
        Ciudad c2 = new Ciudad("Ciudad-Test-2", 15);
        ciudadRepository.save(c1);
        ciudadRepository.save(c2);

        // Crear ServicioCiudad
        ServicioCiudad sc1 = new ServicioCiudad(100, s1, c1);
        ServicioCiudad sc2 = new ServicioCiudad(150, s2, c1);
        ServicioCiudad sc3 = new ServicioCiudad(200, s1, c2);
        
        servicioCiudadRepository.save(sc1);
        servicioCiudadRepository.save(sc2);
        servicioCiudadRepository.save(sc3);
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
    void obtenerServiciosPorCiudadDevuelveListaCorrecta() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();

        // Prueba con token de admin
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/servicios/1")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ServicioCiudadDTO.class)
                .value(servicios -> {
                    System.out.println("Servicios obtenidos (admin): " + servicios);
                    assert servicios != null;
                });

        // Prueba con token de caravanero
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/servicios/1")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ServicioCiudadDTO.class)
                .value(servicios -> {
                    System.out.println("Servicios obtenidos (caravanero): " + servicios);
                    assert servicios != null;
                });

        // Prueba con token de comerciante (debe fallar)
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/servicios/1")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();
    }
}