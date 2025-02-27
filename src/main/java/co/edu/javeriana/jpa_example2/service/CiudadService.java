package co.edu.javeriana.jpa_example2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.model.Ciudad;
import co.edu.javeriana.jpa_example2.repository.CiudadRepository;

public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Ciudad> listarCiudades(){
        return ciudadRepository.findAll();
    }
}
