package co.edu.javeriana.jpa_example2.controller;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import co.edu.javeriana.jpa_example2.dto.CaravanaCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CaravanaCiudadControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private WebTestClient webTestClient;

    public CaravanaCiudadControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear ciudades de prueba
        Ciudad ciudad1 = new Ciudad("Ciudad-Test-1", 50);
        ciudad1 = ciudadRepository.save(ciudad1);

        Ciudad ciudad2 = new Ciudad("Ciudad-Test-2", 75);
        ciudad2 = ciudadRepository.save(ciudad2);

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
        c1.setCiudad_actual(ciudad1);
        caravanaRepository.save(c1);

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
        c2.setCiudad_actual(ciudad1);
        caravanaRepository.save(c2);

        Caravana c3 = new Caravana();
        c3.setFecha_creacion(LocalDateTime.now());
        c3.setNombre("caravana-test-3");
        c3.setVelocidad_base(12.0f);
        c3.setVelocidad_actual(11.0f);
        c3.setCapacidad_base(80.0f);
        c3.setCapacidad_actual(75.0f);
        c3.setDinero(800);
        c3.setSalud_actual(70.0f);
        c3.setSalud_maxima(90.0f);
        c3.setTiempo_acumulado(5000L);
        c3.setTiene_guardias(true);
        c3.setCiudad_actual(ciudad2);
        caravanaRepository.save(c3);
    }

    @Test
    void listarCaravanasPorCiudadDevuelveCaravanasCorrectamente() {
        webTestClient.get()
                .uri(SERVER_URL + "ciudad/caravanas/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CaravanaCiudadDTO.class)
                .value(resultado -> {
                    assert resultado != null;
                    assert resultado.getCiudadId().equals(1L);
                    assert resultado.getCaravanasIds().size() == 2;
                    assert resultado.getCaravanasIds().contains(1L);
                    assert resultado.getCaravanasIds().contains(2L);
                });
    }
}