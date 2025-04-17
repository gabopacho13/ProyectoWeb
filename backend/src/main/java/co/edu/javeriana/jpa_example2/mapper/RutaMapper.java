package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.RutaDTO;
import co.edu.javeriana.jpa_example2.model.Ruta;

public class RutaMapper {
    public static RutaDTO toDTO(Ruta ruta){
        RutaDTO rutaDTO = new RutaDTO();
        rutaDTO.setId(ruta.getId());
        rutaDTO.setEs_segura(ruta.getEs_segura());
        rutaDTO.setDistancia(ruta.getDistancia());
        rutaDTO.setDano(ruta.getDano());
    
        return rutaDTO;
    }
    public static Ruta toEntity(RutaDTO rutaDTO){
        Ruta ruta = new Ruta();
        ruta.setId(rutaDTO.getId());
        ruta.setEs_segura(rutaDTO.getEs_segura());
        ruta.setDistancia(rutaDTO.getDistancia());
        ruta.setDano(rutaDTO.getDano());
        
        return ruta;
    }
}
