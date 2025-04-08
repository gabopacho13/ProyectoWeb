package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.service.TransaccionServicioService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.TransaccionServicioDTO;

@RestController
@RequestMapping("/transaccion/servicios")
public class TransaccionServicioController {

    @Autowired
    private TransaccionServicioService transaccionService;

    @GetMapping("/lista")
    public List<TransaccionServicioDTO> listarTransacciones(){
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/{idTransaccion}")
    public TransaccionServicioDTO buscarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        return transaccionService.buscarTransaccion(idTransaccion).orElse(null);
    }

    @GetMapping("/caravana/{idCaravana}")
    public List<TransaccionServicioDTO> listarTransaccionesPorCaravana(@PathVariable("idCaravana") Long idCaravana){
        return transaccionService.buscarTransaccionesPorCaravana(idCaravana);
    }

    @PostMapping
    public TransaccionServicioDTO crearTransaccion(@RequestBody TransaccionServicioDTO transaccionDTO){
        return transaccionService.guardarTransaccion(transaccionDTO);
    }

    @PutMapping
    public TransaccionServicioDTO modificarTransaccion(@RequestBody TransaccionServicioDTO transaccionDTO){
        return transaccionService.actualizarTransaccion(transaccionDTO);
    }

    @DeleteMapping("/{idTransaccion}")
    public void borrarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        transaccionService.borrarTransaccion(idTransaccion);
    }
}
