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
import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ProductoControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private WebTestClient webTestClient;

        @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public ProductoControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        Producto p1 = new Producto("Producto-Test-1");
        productoRepository.save(p1);

        Producto p2 = new Producto("Producto-Test-2");
        productoRepository.save(p2);

        Producto p3 = new Producto("Producto-Test-3");
        productoRepository.save(p3);
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
    void obtenerProductoPorIdDevuelveProductoEsperado() {
        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        webTestClient.get()
                .uri(SERVER_URL + "producto/2")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductoDTO.class)
                .value(producto -> {
                    assert producto != null;
                    assert producto.getId().equals(2L);
                    assert producto.getNombre().equals("Producto-Test-2");
                });

        webTestClient.get()
                .uri(SERVER_URL + "producto/2")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductoDTO.class)
                .value(producto -> {
                    assert producto != null;
                    assert producto.getId().equals(2L);
                    assert producto.getNombre().equals("Producto-Test-2");
                });

        webTestClient.get()
                .uri(SERVER_URL + "producto/2")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductoDTO.class)
                .value(producto -> {
                    assert producto != null;
                    assert producto.getId().equals(2L);
                    assert producto.getNombre().equals("Producto-Test-2");
                });
    }
}