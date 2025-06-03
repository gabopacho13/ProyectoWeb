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

import co.edu.javeriana.jpa_example2.dto.CiudadRutasOrigenDTO;
import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
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
public class CiudadRutasOrigenControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public CiudadRutasOrigenControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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
        ruta1.setOrigen(bogota);
        ruta1.setDestino(cali);
        rutaRepository.save(ruta1);

        Ruta ruta2 = new Ruta();
        ruta2.setDistancia(456.8f);
        ruta2.setEs_segura(false);
        ruta2.setDano(15.2f);
        ruta2.setOrigen(bogota);
        ruta2.setDestino(medellin);
        rutaRepository.save(ruta2);
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
    void getCiudadRutasOrigen() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Admin access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/origen/1")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadRutasOrigenDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getCiudadId().equals(1L);
                    assert resultado.getRutasOrigenIds().size() == 2;
                    assert resultado.getRutasOrigenIds().contains(1L);
                    assert resultado.getRutasOrigenIds().contains(2L);
                });

        // Caravanero access
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/origen/1")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CiudadRutasOrigenDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getCiudadId().equals(1L);
                    assert resultado.getRutasOrigenIds().size() == 2;
                    assert resultado.getRutasOrigenIds().contains(1L);
                    assert resultado.getRutasOrigenIds().contains(2L);
                });

        // Comerciante access (should be forbidden)
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/origen/1")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isForbidden();
    }
}