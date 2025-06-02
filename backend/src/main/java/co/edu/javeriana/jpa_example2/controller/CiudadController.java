package co.edu.javeriana.jpa_example2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {
    @Autowired
    private CiudadService ciudadService;

    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Secured({Role.Code.CARAVANERO})
    @GetMapping("/lista")
    public List<CiudadDTO> listarCiudades(){
        log.info("Lista de Ciudades");
        return ciudadService.listarCiudades();
    }

    @Secured({Role.Code.CARAVANERO})
    @GetMapping("/{idCiudad}")
    public CiudadDTO listarCiudad(@PathVariable("idCiudad") Long id){
        return ciudadService.buscarCiudad(id).orElseThrow();
    }

    
    @PostMapping
    public CiudadDTO crearCiudad(@RequestBody CiudadDTO ciudadDTO){
        return ciudadService.guardarCiudad(ciudadDTO);
    }

    @PutMapping
    public CiudadDTO editarCiudad(@RequestBody CiudadDTO ciudadDTO) {
        return ciudadService.actualizarCiudad(ciudadDTO);
    }

    @DeleteMapping("/{idCiudad}")
    public void borrarCiudad(@PathVariable("idCiudad") Long id){
        ciudadService.borrarCiudad(id);
    }
}
