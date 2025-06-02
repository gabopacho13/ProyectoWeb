package co.edu.javeriana.jpa_example2.controller;

import java.sql.Date;
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

import co.edu.javeriana.jpa_example2.dto.TransaccionServicioDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Servicio;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ServicioRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionServicioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class TransaccionServicioControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private TransaccionServicioRepository transaccionServicioRepository;
    
    @Autowired
    private CaravanaRepository caravanaRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    public TransaccionServicioControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear Caravana de prueba
        Caravana caravana = new Caravana();
        caravana.setFecha_creacion(LocalDateTime.now());
        caravana.setNombre("caravana-servicio-test");
        caravana.setVelocidad_base(12.0f);
        caravana.setVelocidad_actual(10.8f);
        caravana.setCapacidad_base(110.0f);
        caravana.setCapacidad_actual(80.0f);
        caravana.setDinero(3000);
        caravana.setSalud_actual(70.0f);
        caravana.setSalud_maxima(100.0f);
        caravana.setTiempo_acumulado(2400L);
        caravana.setTiene_guardias(false);
        caravanaRepository.save(caravana);

        // Crear Servicio de prueba
        Servicio servicio = new Servicio();
        servicio.setTipo("REPARACION");
        servicio.setEfecto("Aumenta la salud de la caravana");
        servicioRepository.save(servicio);

        // Crear Ciudad de prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Cartagena");
        ciudad.setImpuesto_entrada(20);
        ciudadRepository.save(ciudad);
    }

    @Test
    void crearTransaccionServicio() {
        // Preparar DTO para la transacción de servicio
        TransaccionServicioDTO transaccionDTO = new TransaccionServicioDTO();
        transaccionDTO.setIdCaravana(1L);
        transaccionDTO.setIdServicio(1L);
        transaccionDTO.setIdCiudad(1L);
        transaccionDTO.setTipo("CONTRATACION");
        transaccionDTO.setCantidad(1);
        transaccionDTO.setPrecioUnitario(250.0f);
        transaccionDTO.setFecha(new Date(System.currentTimeMillis()));

        webTestClient.post()
                .uri(SERVER_URL + "transaccion/servicios")
                .bodyValue(transaccionDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransaccionServicioDTO.class)
                .value(transaccion -> {
                    // Validaciones del objeto creado
                    assert transaccion != null;
                    assert transaccion.getId() != null;
                    assert transaccion.getIdCaravana().equals(1L);
                    assert transaccion.getIdServicio().equals(1L);
                    assert transaccion.getIdCiudad().equals(1L);
                    assert transaccion.getTipo().equals("CONTRATACION");
                    assert transaccion.getCantidad() == 1;
                    assert transaccion.getPrecioUnitario() == 250.0f;
                    assert transaccion.getFecha() != null;
                });
    }
}