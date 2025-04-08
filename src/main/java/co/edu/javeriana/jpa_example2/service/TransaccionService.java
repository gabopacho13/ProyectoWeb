package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.TransaccionDTO;
import co.edu.javeriana.jpa_example2.mapper.TransaccionMapper;
import co.edu.javeriana.jpa_example2.model.Transaccion;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionRepository;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Producto;

@Service
public class TransaccionService {
    
    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<TransaccionDTO> listarTransacciones() {
        return transaccionRepository.findAll().stream()
                .map(TransaccionMapper::toDTO)
                .toList();
    }

    public Optional<TransaccionDTO> buscarTransaccion(Long id) {
        return transaccionRepository.findById(id)
                .map(TransaccionMapper::toDTO);
    }

    public List<TransaccionDTO> buscarTransaccionesPorCaravana(Long idCaravana){
        return caravanaRepository.findById(idCaravana)
                .map(Caravana::getTransacciones)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la caravana con id: " + idCaravana))
                .stream()
                .map(TransaccionMapper::toDTO)
                .toList();
    }

    public TransaccionDTO guardarTransaccion(TransaccionDTO transaccionDTO) {
        transaccionDTO.setId(null);
        return TransaccionMapper.toDTO(transaccionRepository.save(TransaccionMapper.toEntity(transaccionDTO)));
    }

    public TransaccionDTO actualizarTransaccion(TransaccionDTO transaccionDTO) {
        if (transaccionDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la transaccion no puede ser nulo");
        }
        Transaccion transaccion = transaccionRepository.findById(transaccionDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la transaccion con id: " + transaccionDTO.getId()));
        Optional<Caravana> caravanaOptional = caravanaRepository.findById(transaccionDTO.getIdCaravana());
        if (caravanaOptional.isPresent()) {
            Caravana caravana = caravanaOptional.get();
            transaccion.setCaravana(caravana);
        } else {
            throw new IllegalArgumentException("No se encontró la caravana con id: " + transaccionDTO.getIdCaravana());
        }
        Optional<Ciudad> ciudadOptional = ciudadRepository.findById(transaccionDTO.getIdCiudad());
        if (ciudadOptional.isPresent()) {
            Ciudad ciudad = ciudadOptional.get();
            transaccion.setCiudad(ciudad);
        } else {
            throw new IllegalArgumentException("No se encontró la ciudad con id: " + transaccionDTO.getIdCiudad());
        }
        Optional<Producto> productoOptional = productoRepository.findById(transaccionDTO.getIdProducto());
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            transaccion.setProducto(producto);
        } else {
            throw new IllegalArgumentException("No se encontró el producto con id: " + transaccionDTO.getIdProducto());
        }
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccion.setCantidad(transaccionDTO.getCantidad());
        transaccion.setPrecio_unitario(transaccionDTO.getPrecioUnitario());
        transaccion.setFecha(transaccionDTO.getFecha());
        return TransaccionMapper.toDTO(transaccionRepository.save(transaccion));
    }

    public void borrarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
