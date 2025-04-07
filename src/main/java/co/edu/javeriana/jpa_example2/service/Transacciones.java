package co.edu.javeriana.jpa_example2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.javeriana.jpa_example2.dto.TransaccionDTO;
import co.edu.javeriana.jpa_example2.mapper.TransaccionMapper;

import co.edu.javeriana.jpa_example2.repository.TransaccionRepository;

@Service
public class Transacciones {
    
    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<TransaccionDTO> listarTransacciones() {
        return transaccionRepository.findAll().stream()
                .map(TransaccionMapper::toDTO)
                .toList();
    }

    public Optional<TransaccionDTO> buscarTransaccion(Long id) {
        return transaccionRepository.findById(id)
                .map(TransaccionMapper::toDTO);
    }

    public TransaccionDTO guardarTransaccion(TransaccionDTO transaccionDTO) {
        transaccionDTO.setId(null);
        return TransaccionMapper.toDTO(transaccionRepository.save(TransaccionMapper.toEntity(transaccionDTO)));
    }

    public TransaccionDTO actualizarTransaccion(TransaccionDTO transaccionDTO) {
        if (transaccionDTO.getId() == null) {
            throw new IllegalArgumentException("El id de la transaccion no puede ser nulo");
        }
        return TransaccionMapper.toDTO(transaccionRepository.save(TransaccionMapper.toEntity(transaccionDTO)));
    }

    public void borrarTransaccion(Long id) {
        transaccionRepository.deleteById(id);
    }
}
