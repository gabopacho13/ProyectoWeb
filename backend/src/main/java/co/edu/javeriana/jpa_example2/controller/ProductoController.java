package co.edu.javeriana.jpa_example2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import co.edu.javeriana.jpa_example2.dto.ProductoDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{id}")
    public ProductoDTO obtenerProducto(@PathVariable Long id) {
        return productoService.buscarProducto(id).orElseThrow();
    }

    @Secured({Role.Code.ADMIN})
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.guardarProducto(productoDTO);
    }
    
    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        productoDTO.setId(id);
        return productoService.actualizarProducto(productoDTO);
    }
    
    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @DeleteMapping("/{id}")
    public void borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
    }
}
