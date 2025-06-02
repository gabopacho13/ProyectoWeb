package co.edu.javeriana.jpa_example2.controller;

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

import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.model.Producto;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("integration-testing")
public class ProductoControllerIntegrationTest {

    private String SERVER_URL;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private WebTestClient webTestClient;

    public ProductoControllerIntegrationTest(@Value("${server.port}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort + "/";
    }

    @BeforeEach
    void init() {
        Producto p1 = new Producto("Producto-Test-1");
        productoRepository.save(p1);

        Producto p2 = new Producto("Producto-Test-2");
        productoRepository.save(p2);

        Producto p3 = new Producto("Producto-Test-3");
        productoRepository.save(p3);
    }

    @Test
    void obtenerProductoPorIdDevuelveProductoEsperado() {
        webTestClient.get()
                .uri(SERVER_URL + "producto/2")
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