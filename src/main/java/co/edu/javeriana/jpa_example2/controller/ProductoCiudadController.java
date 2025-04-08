package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.service.ProductoCiudadService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;

@RestController
@RequestMapping("/ciudad/producto")
public class ProductoCiudadController {
    
    @Autowired
    private ProductoCiudadService productoCiudadService;

    @GetMapping("/{idCiudad}")
    public List<ProductoCiudadDTO> getProductosCiudad(@PathVariable Long idCiudad) {
        return productoCiudadService.buscarPorCiudadId(idCiudad);
    }

    @GetMapping("/lista")
    public List<ProductoCiudadDTO> listarProductosCiudades() {
        return productoCiudadService.listarProductosCiudades();
    }

    @PutMapping
    public ProductoCiudadDTO actualizarProductoCiudad(@RequestBody ProductoCiudadDTO productoCiudadDTO) {
        return productoCiudadService.actualizarProductoCiudad(productoCiudadDTO);
    }

    @PostMapping
    public ProductoCiudadDTO crearProductoCiudad(@RequestBody ProductoCiudadDTO productoCiudadDTO) {
        return productoCiudadService.guardarProductoCiudad(productoCiudadDTO);
    }

    @DeleteMapping("/{idProductoCiudad}")
    public void borrarProductoCiudad(@PathVariable("idProductoCiudad") Long id) {
        productoCiudadService.borrarProductoCiudad(id);
    }
}
