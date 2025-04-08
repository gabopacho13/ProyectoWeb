package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.TransaccionProductoDTO;
import co.edu.javeriana.jpa_example2.mapper.TransaccionProductoMapper;
import co.edu.javeriana.jpa_example2.model.TransaccionProducto;
import co.edu.javeriana.jpa_example2.repository.CaravanaRepository;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;
import co.edu.javeriana.jpa_example2.repository.ProductoRepository;
import co.edu.javeriana.jpa_example2.repository.TransaccionProductoRepository;
import co.edu.javeriana.jpa_example2.model.Caravana;
import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.model.Producto;

@Service
public class TransaccionProductoService {
    
    @Autowired
    private TransaccionProductoRepository transaccionRepository;
    @Autowired
    private CaravanaRepository caravanaRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private ProductoRepository productoRepository;

    public List<TransaccionProductoDTO> listarTransacciones() {
        return transaccionRepository.findAll().stream()
                .map(TransaccionProductoMapper::toDTO)
                .toList();
    }

    public Optional<TransaccionProductoDTO> buscarTransaccion(Long id) {
        return transaccionRepository.findById(id)
                .map(TransaccionProductoMapper::toDTO);
    }

    public List<TransaccionProductoDTO> buscarTransaccionesPorCaravana(Long idCaravana){
        return caravanaRepository.findById(idCaravana)
                .map(Caravana::getTransaccionesProducto)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la caravana con id: " + idCaravana))
                .stream()
                .map(TransaccionProductoMapper::toDTO)
                .toList();
    }

    public TransaccionProductoDTO guardarTransaccion(TransaccionProductoDTO transaccionDTO) {
        transaccionDTO.setId(null);
        return TransaccionProductoMapper.toDTO(transaccionRepository.save(TransaccionProductoMapper.toEntity(transaccionDTO)));
    }

    public TransaccionProductoDTO actualizarTransaccion(TransaccionProductoDTO transaccionDTO) {
        if (transaccionDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la transaccion no puede ser nulo");
        }
        TransaccionProducto transaccion = transaccionRepository.findById(transaccionDTO.getId())
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
        return TransaccionProductoMapper.toDTO(transaccionRepository.save(transaccion));
    }

    public void borrarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
