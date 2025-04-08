package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.service.TransaccionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.TransaccionDTO;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/lista")
    public List<TransaccionDTO> listarTransacciones(){
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/{idTransaccion}")
    public TransaccionDTO buscarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        return transaccionService.buscarTransaccion(idTransaccion).orElse(null);
    }

    @GetMapping("/caravana/{idCaravana}")
    public List<TransaccionDTO> listarTransaccionesPorCaravana(@PathVariable("idCaravana") Long idCaravana){
        return transaccionService.buscarTransaccionesPorCaravana(idCaravana);
    }

    @PostMapping
    public TransaccionDTO crearTransaccion(@RequestBody TransaccionDTO transaccionDTO){
        return transaccionService.guardarTransaccion(transaccionDTO);
    }

    @PutMapping
    public TransaccionDTO modificarTransaccion(@RequestBody TransaccionDTO transaccionDTO){
        return transaccionService.actualizarTransaccion(transaccionDTO);
    }

    @DeleteMapping("/{idTransaccion}")
    public void borrarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        transaccionService.borrarTransaccion(idTransaccion);
    }
}
