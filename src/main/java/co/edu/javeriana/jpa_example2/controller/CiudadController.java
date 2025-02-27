package co.edu.javeriana.jpa_example2.controller;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.jpa_example2.service.CiudadService;

public class CiudadController {
    @Autowired
    private CiudadService ciudadService;
    
    void listarCiudades(){
        ciudadService.listarCiudades();
    }
}
