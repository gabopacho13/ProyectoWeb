package co.edu.javeriana.jpa_example2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;

@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Ciudad> listarCiudades(){
        return ciudadRepository.findAll();
    }

    public Ciudad buscarCiudad(Long id){
        return ciudadRepository.findById(id).orElseThrow();
    }
}
