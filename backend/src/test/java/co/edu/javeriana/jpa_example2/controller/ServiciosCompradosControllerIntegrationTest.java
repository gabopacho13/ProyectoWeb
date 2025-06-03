package co.edu.javeriana.jpa_example2.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
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
import co.edu.javeriana.jpa_example2.dto.ServiciosCompradosDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.ServiciosCompradosRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ServiciosCompradosControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServiciosCompradosRepository serviciosCompradosRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private TestRestTemplate rest;

    public ServiciosCompradosControllerIntegrationTest(@Value("${server.port}") int serverPort) {
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

        // Crear servicios de prueba
        Servicio s1 = new Servicio("Reparación", "Aumenta la salud de la caravana");
        servicioRepository.save(s1);

        Servicio s2 = new Servicio("Suministros", "Aumenta la capacidad actual");
        servicioRepository.save(s2);

        Servicio s3 = new Servicio("Guardias", "Mejora la seguridad de la caravana");
        servicioRepository.save(s3);
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
    void crearServicioCompradoDevuelveServicioConDatosCorrectos() {
        // Crear DTO para el POST
        ServiciosCompradosDTO nuevoServicioDTO = new ServiciosCompradosDTO();
        nuevoServicioDTO.setIdCaravana(1L);
        nuevoServicioDTO.setIdServicio(1L);
        nuevoServicioDTO.setFechaCompra(new Date(System.currentTimeMillis()));

        String adminToken = login("charlie@charlie.com", "charlie123").getToken();
        String comercianteToken = login("alice@alice.com", "alice123").getToken();
        String caravaneroToken = login("bob@bob.com", "bob123").getToken();

        // Prueba con token de caravanero (debe tener acceso)
        webTestClient.post()
                .uri(SERVER_URL + "caravana/servicios")
                .headers(headers -> headers.setBearerAuth(caravaneroToken))
                .bodyValue(nuevoServicioDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ServiciosCompradosDTO.class)
                .value(servicioComprado -> {
                    // Validaciones del contenido del objeto devuelto
                    assert servicioComprado != null;
                    assert servicioComprado.getId() != null;
                    assert servicioComprado.getIdCaravana().equals(1L);
                    assert servicioComprado.getIdServicio().equals(1L);
                    assert servicioComprado.getFechaCompra() != null;
                });

        // Prueba con token de admin (debe tener acceso)
        webTestClient.post()
                .uri(SERVER_URL + "caravana/servicios")
                .headers(headers -> headers.setBearerAuth(adminToken))
                .bodyValue(nuevoServicioDTO)
                .exchange()
                .expectStatus().isOk();

        // Prueba con token de comerciante (no debe tener acceso)
        webTestClient.post()
                .uri(SERVER_URL + "caravana/servicios")
                .headers(headers -> headers.setBearerAuth(comercianteToken))
                .bodyValue(nuevoServicioDTO)
                .exchange()
                .expectStatus().isForbidden();
    }
}