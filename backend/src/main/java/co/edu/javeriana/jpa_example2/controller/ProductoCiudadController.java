package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.service.ProductoCiudadService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.ProductoCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Role;

@RestController
@RequestMapping("/ciudad/producto")
public class ProductoCiudadController {
    
    @Autowired
    private ProductoCiudadService productoCiudadService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idCiudad}")
    public List<ProductoCiudadDTO> getProductosCiudad(@PathVariable Long idCiudad) {
        return productoCiudadService.buscarPorCiudadId(idCiudad);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<ProductoCiudadDTO> listarProductosCiudades() {
        return productoCiudadService.listarProductosCiudades();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping
    public ProductoCiudadDTO actualizarProductoCiudad(@RequestBody ProductoCiudadDTO productoCiudadDTO) {
        return productoCiudadService.actualizarProductoCiudad(productoCiudadDTO);
    }

    @Secured({Role.Code.ADMIN})
    @PostMapping
    public ProductoCiudadDTO crearProductoCiudad(@RequestBody ProductoCiudadDTO productoCiudadDTO) {
        return productoCiudadService.guardarProductoCiudad(productoCiudadDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @DeleteMapping("/{idProductoCiudad}")
    public void borrarProductoCiudad(@PathVariable("idProductoCiudad") Long id) {
        productoCiudadService.borrarProductoCiudad(id);
    }
}
