package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.CiudadDTO;
import co.edu.javeriana.jpa_example2.model.Ciudad;

public class CiudadMapper {
    public static CiudadDTO toDTO(Ciudad ciudad){
        CiudadDTO ciudadDTO = new CiudadDTO();
        ciudadDTO.setId(ciudad.getId());
        ciudadDTO.setNombre(ciudad.getNombre());
        ciudadDTO.setImpuesto_entrada(ciudad.getImpuesto_entrada());
        
        return ciudadDTO;
    }
    public static Ciudad toEntity(CiudadDTO ciudadDTO){
        Ciudad ciudad = new Ciudad();
        ciudad.setId(ciudadDTO.getId());
        ciudad.setNombre(ciudadDTO.getNombre());
        ciudad.setImpuesto_entrada(ciudadDTO.getImpuesto_entrada());
        
        return ciudad;
    }
}
