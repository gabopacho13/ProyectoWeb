package co.edu.javeriana.jpa_example2.controller;

import co.edu.javeriana.jpa_example2.dto.InventarioCaravanaDTO;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import co.edu.javeriana.jpa_example2.mapper.CompraventaMapper;
import co.edu.javeriana.jpa_example2.model.*;
import co.edu.javeriana.jpa_example2.repository.*;
import co.edu.javeriana.jpa_example2.service.CompraventaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/compraventa")
public class CompraventaController {

    @Autowired private CaravanaRepository caravanaRepository;
    @Autowired private CiudadRepository ciudadRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ProductoCiudadRepository productoCiudadRepository;
    @Autowired private CompraventaService compraventaService;
    @Autowired private CompraventaMapper compraventaMapper;

    @GetMapping("/precio-compra")
    public ProductoCiudadDTO obtenerPrecioCompra(@RequestParam Long ciudadId, @RequestParam Long productoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        ProductoCiudad pc = ciudad.getProductoCiudades().stream()
                .filter(p -> p.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no disponible en ciudad"));

        return compraventaMapper.toProductoCiudadDTO(pc);
    }

    @GetMapping("/precio-venta")
    public ProductoCiudadDTO obtenerPrecioVenta(@RequestParam Long ciudadId, @RequestParam Long productoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        ProductoCiudad pc = ciudad.getProductoCiudades().stream()
                .filter(p -> p.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no disponible en ciudad"));

        return compraventaMapper.toProductoCiudadDTO(pc);
    }

    @PostMapping("/comprar")
    public InventarioCaravanaDTO comprar(
            @RequestParam Long caravanaId,
            @RequestParam Long ciudadId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {

        Caravana caravana = caravanaRepository.findById(caravanaId)
                .orElseThrow(() -> new RuntimeException("Caravana no encontrada"));
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        compraventaService.comprar(caravana, ciudad, producto.getNombre(), cantidad);

        InventarioCaravana inventario = caravana.getInventarioCaravanas().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Inventario no actualizado"));

        return compraventaMapper.toInventarioCaravanaDTO(inventario);
    }

    @PostMapping("/vender")
    public InventarioCaravanaDTO vender(
            @RequestParam Long caravanaId,
            @RequestParam Long ciudadId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {

        Caravana caravana = caravanaRepository.findById(caravanaId)
                .orElseThrow(() -> new RuntimeException("Caravana no encontrada"));
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        compraventaService.vender(caravana, ciudad, producto.getNombre(), cantidad);

        InventarioCaravana inventario = caravana.getInventarioCaravanas().stream()
                .filter(i -> i.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Inventario no actualizado"));

        return compraventaMapper.toInventarioCaravanaDTO(inventario);
    }
}