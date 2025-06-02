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

import co.edu.javeriana.jpa_example2.dto.TransaccionProductoDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionProductoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class TransaccionProductoControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private TransaccionProductoRepository transaccionProductoRepository;
    
    @Autowired
    private CaravanaRepository caravanaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private WebTestClient webTestClient;

    public TransaccionProductoControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        // Crear Caravana de prueba
        Caravana caravana = new Caravana();
        caravana.setFecha_creacion(LocalDateTime.now());
        caravana.setNombre("caravana-transaccion-test");
        caravana.setVelocidad_base(10.0f);
        caravana.setVelocidad_actual(9.5f);
        caravana.setCapacidad_base(100.0f);
        caravana.setCapacidad_actual(75.0f);
        caravana.setDinero(2500);
        caravana.setSalud_actual(90.0f);
        caravana.setSalud_maxima(100.0f);
        caravana.setTiempo_acumulado(1800L);
        caravana.setTiene_guardias(true);
        caravanaRepository.save(caravana);

        // Crear Producto de prueba
        Producto producto = new Producto();
        producto.setNombre("Especias");
        productoRepository.save(producto);

        // Crear Ciudad de prueba
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Medellín");
        ciudad.setImpuesto_entrada(15);
        ciudadRepository.save(ciudad);
    }

    @Test
    void crearTransaccionProducto() {
        // Preparar DTO para la transacción
        TransaccionProductoDTO transaccionDTO = new TransaccionProductoDTO();
        transaccionDTO.setIdCaravana(1L);
        transaccionDTO.setIdProducto(1L);
        transaccionDTO.setIdCiudad(1L);
        transaccionDTO.setTipo("COMPRA");
        transaccionDTO.setCantidad(50);
        transaccionDTO.setPrecioUnitario(12.5f);
        transaccionDTO.setFecha(new Date(System.currentTimeMillis()));

        webTestClient.post()
                .uri(SERVER_URL + "transaccion/productos")
                .bodyValue(transaccionDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransaccionProductoDTO.class)
                .value(transaccion -> {
                    // Validaciones del objeto creado
                    assert transaccion != null;
                    assert transaccion.getId() != null;
                    assert transaccion.getIdCaravana().equals(1L);
                    assert transaccion.getIdProducto().equals(1L);
                    assert transaccion.getIdCiudad().equals(1L);
                    assert transaccion.getTipo().equals("COMPRA");
                    assert transaccion.getCantidad() == 50;
                    assert transaccion.getPrecioUnitario() == 12.5f;
                    assert transaccion.getFecha() != null;
                });
    }
}