package co.edu.javeriana.jpa_example2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.jpa_example2.service.TransaccionProductoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import co.edu.javeriana.jpa_example2.dto.TransaccionProductoDTO;

@RestController
@RequestMapping("/transaccion/productos")
public class TransaccionProductoController {

    @Autowired
    private TransaccionProductoService transaccionService;

    @GetMapping("/lista")
    public List<TransaccionProductoDTO> listarTransacciones(){
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/{idTransaccion}")
    public TransaccionProductoDTO buscarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        return transaccionService.buscarTransaccion(idTransaccion).orElse(null);
    }

    @GetMapping("/caravana/{idCaravana}")
    public List<TransaccionProductoDTO> listarTransaccionesPorCaravana(@PathVariable("idCaravana") Long idCaravana){
        return transaccionService.buscarTransaccionesPorCaravana(idCaravana);
    }

    @PostMapping
    public TransaccionProductoDTO crearTransaccion(@RequestBody TransaccionProductoDTO transaccionDTO){
        return transaccionService.guardarTransaccion(transaccionDTO);
    }

    @PutMapping
    public TransaccionProductoDTO modificarTransaccion(@RequestBody TransaccionProductoDTO transaccionDTO){
        return transaccionService.actualizarTransaccion(transaccionDTO);
    }

    @DeleteMapping("/{idTransaccion}")
    public void borrarTransaccion(@PathVariable("idTransaccion") Long idTransaccion){
        transaccionService.borrarTransaccion(idTransaccion);
    }
}
