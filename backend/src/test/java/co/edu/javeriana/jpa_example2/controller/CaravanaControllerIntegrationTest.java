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

import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class CaravanaControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private WebTestClient webTestClient;

    public CaravanaControllerIntegrationTest(@Value("${server.port}") int serverPort){
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
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

        Caravana c3 = new Caravana();
        c3.setFecha_creacion(LocalDateTime.now());
        c3.setNombre("caravana-prueba-3");
        c3.setVelocidad_base(7.5f);
        c3.setVelocidad_actual(6.3f);
        c3.setCapacidad_base(90.0f);
        c3.setCapacidad_actual(70.0f);
        c3.setDinero(800);
        c3.setSalud_actual(60.0f);
        c3.setSalud_maxima(80.0f);
        c3.setTiempo_acumulado(4800L);
        c3.setTiene_guardias(true);
        caravanaRepository.save(c3);
    }

    @Test
    void caravana(){  
        webTestClient.get()
            .uri(SERVER_URL+"caravana/2")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Caravana.class)
            .returnResult().getResponseBody();
    
    }

    @Test
    void caravanaDevueltaTieneDatosEsperados() {
        webTestClient.get()
            .uri(SERVER_URL + "caravana/2")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Caravana.class)
            .value(caravana -> {
                // Validaciones del contenido del objeto
                assert caravana != null;
                assert caravana.getNombre().equals("caravana-prueba-2");
                assert !caravana.getTiene_guardias();
                assert caravana.getDinero() == 2000;
            });
    }

}
