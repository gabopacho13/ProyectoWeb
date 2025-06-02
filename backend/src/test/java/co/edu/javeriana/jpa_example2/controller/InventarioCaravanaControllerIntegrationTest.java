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

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.InventarioCaravana;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.InventarioCaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;

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

    public InventarioCaravanaControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
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

    @Test
    void crearInventarioCaravanaYValidarRespuesta() {
        InventarioCaravanaDTO nuevoInventario = new InventarioCaravanaDTO();
        nuevoInventario.setCaravanaId(1L);
        nuevoInventario.setProductoId(3L);
        nuevoInventario.setCantidad(15);

        webTestClient.post()
                .uri(SERVER_URL + "Caravana/Inventario")
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