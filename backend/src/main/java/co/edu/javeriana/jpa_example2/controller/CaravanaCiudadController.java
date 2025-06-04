package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.dto.CaravanaCiudadDTO;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.service.CiudadService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/ciudad/caravanas")
public class CaravanaCiudadController {

    @Autowired
    CiudadService ciudadService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idCiudad}")
    public CaravanaCiudadDTO listarCaravanas(@PathVariable("idCiudad") Long idCiudad) {
        return ciudadService.listarCaravanasPorCiudad(idCiudad);
    } 

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping("/actualizar/{idCiudad}")
    public CaravanaCiudadDTO editarCaravanas(@PathVariable("idCiudad") Long idCiudad, @RequestBody CaravanaCiudadDTO caravanaCiudadDTO) {
        List<Long> caravanas = caravanaCiudadDTO.getCaravanasIds();
        return ciudadService.editarCaravanasPorCiudad(idCiudad, caravanas);
    }
}
