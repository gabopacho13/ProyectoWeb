package co.edu.javeriana.jpa_example2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public ProductoDTO obtenerProducto(@PathVariable Long id) {
        return productoService.buscarProducto(id).orElseThrow();
    }

    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.guardarProducto(productoDTO);
    }
    
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        productoDTO.setId(id);
        return productoService.actualizarProducto(productoDTO);
    }
    
    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
    }
}
