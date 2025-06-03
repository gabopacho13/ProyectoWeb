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

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class InventarioCaravanaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private InventarioCaravanaRepository inventarioCaravanaRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public InventarioCaravanaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        // Crear productos de prueba
        Producto p1 = new Producto("Agua");
        productoRepository.save(p1);

        Producto p2 = new Producto("Comida");
        productoRepository.save(p2);

        Producto p3 = new Producto("Medicinas");
        productoRepository.save(p3);

        // Crear caravanas de prueba
        Caravana c1 = new Caravana();
        c1.setFecha_creacion(LocalDateTime.now());
        c1.setNombre("caravana-prueba-1");
        c1.setVelocidad_base(10.5f);
        c1.setVelocidad_actual(9.8f);
        c1.setCapacidad_base(100.0f);
        c1.setCapacidad_actual(85.0f);
        c1.setDinero(1500);
        c1.setSalud_actual(80.0f);
        c1.setSalud_maxima(100.0f);
        c1.setTiempo_acumulado(3600L);
        c1.setTiene_guardias(true);
        caravanaRepository.save(c1);

        Caravana c2 = new Caravana();
        c2.setFecha_creacion(LocalDateTime.now());
        c2.setNombre("caravana-prueba-2");
        c2.setVelocidad_base(8.0f);
        c2.setVelocidad_actual(8.0f);
        c2.setCapacidad_base(120.0f);
        c2.setCapacidad_actual(120.0f);
        c2.setDinero(2000);
        c2.setSalud_actual(95.0f);
        c2.setSalud_maxima(100.0f);
        c2.setTiempo_acumulado(2700L);
        c2.setTiene_guardias(false);
        caravanaRepository.save(c2);

        // Crear inventarios de prueba
        InventarioCaravana inv1 = new InventarioCaravana(50, c1, p1);
        inventarioCaravanaRepository.save(inv1);

        InventarioCaravana inv2 = new InventarioCaravana(30, c1, p2);
        inventarioCaravanaRepository.save(inv2);

        InventarioCaravana inv3 = new InventarioCaravana(20, c2, p1);
        inventarioCaravanaRepository.save(inv3);
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
    void crearInventarioCaravanaYValidarRespuesta() {
        InventarioCaravanaDTO nuevoInventario = new InventarioCaravanaDTO();
        nuevoInventario.setCaravanaId(1L);
        nuevoInventario.setProductoId(3L);
        nuevoInventario.setCantidad(15);

        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Test with admin token
        webTestClient.post()
                .uri(SERVER_URL + "Caravana/Inventario")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .bodyValue(nuevoInventario)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventarioCaravanaDTO.class)
                .value(inventario -> {
                    assert inventario != null;
                    assert inventario.getId() != null;
                    assert inventario.getCaravanaId().equals(1L);
                    assert inventario.getProductoId().equals(3L);
                    assert inventario.getCantidad() == 15;
                });

        // Test with comerciante token
        webTestClient.post()
                .uri(SERVER_URL + "Caravana/Inventario")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .bodyValue(nuevoInventario)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventarioCaravanaDTO.class)
                .value(inventario -> {
                    assert inventario != null;
                    assert inventario.getId() != null;
                    assert inventario.getCaravanaId().equals(1L);
                    assert inventario.getProductoId().equals(3L);
                    assert inventario.getCantidad() == 15;
                });

        // Test with caravanero token
        webTestClient.post()
                .uri(SERVER_URL + "Caravana/Inventario")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .bodyValue(nuevoInventario)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventarioCaravanaDTO.class)
                .value(inventario -> {
                    assert inventario != null;
                    assert inventario.getId() != null;
                    assert inventario.getCaravanaId().equals(1L);
                    assert inventario.getProductoId().equals(3L);
                    assert inventario.getCantidad() == 15;
                });
    }
}