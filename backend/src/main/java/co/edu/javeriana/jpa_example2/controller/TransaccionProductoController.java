package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import co.edu.javeriana.jpa_example2.service.TransaccionProductoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.TransaccionProductoDTO;
import co.edu.javeriana.jpa_example2.model.Role;

@RestController
@RequestMapping("/transaccion/productos")
public class TransaccionProductoController {

    @Autowired
    private TransaccionProductoService transaccionService;

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/lista")
    public List<TransaccionProductoDTO> listarTransacciones(){
        return transaccionService.listarTransacciones();
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/{idTransaccion}")
    public TransaccionProductoDTO buscarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        return transaccionService.buscarTransaccion(idTransaccion).orElse(null);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @GetMapping("/caravana/{idCaravana}")
    public List<TransaccionProductoDTO> listarTransaccionesPorCaravana(@PathVariable("idCaravana") Long idCaravana){
        return transaccionService.buscarTransaccionesPorCaravana(idCaravana);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PostMapping
    public TransaccionProductoDTO crearTransaccion(@RequestBody TransaccionProductoDTO transaccionDTO){
        return transaccionService.guardarTransaccion(transaccionDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @PutMapping
    public TransaccionProductoDTO modificarTransaccion(@RequestBody TransaccionProductoDTO transaccionDTO){
        return transaccionService.actualizarTransaccion(transaccionDTO);
    }

    @Secured({Role.Code.CARAVANERO, Role.Code.COMERCIANTE, Role.Code.ADMIN})
    @DeleteMapping("/{idTransaccion}")
    public void borrarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        transaccionService.borrarTransaccion(idTransaccion);
    }
}
