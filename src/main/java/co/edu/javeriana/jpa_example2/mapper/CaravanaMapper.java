package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.CaravanaDTO;
import co.edu.javeriana.jpa_example2.model.Caravana;

public class CaravanaMapper {

    // Convierte de entidad a DTO
    public static CaravanaDTO toDTO(Caravana caravana) {
        if (caravana == null) {
            return null;
        }
        return new CaravanaDTO(
            caravana.getId(),
            caravana.getFecha_creacion(),
            caravana.getNombre(),
            caravana.getVelocidad_base(),
            caravana.getVelocidad_actual(),
            caravana.getCapacidad_base(),
            caravana.getCapacidad_actual(),
            caravana.getDinero(),
            caravana.getSalud_actual(),
            caravana.getSalud_maxima(),
            caravana.getTiempo_acumulado(),
            caravana.getTiene_guardias()
        );
    }

    // Convierte de DTO a entidad
    public static Caravana toEntity(CaravanaDTO caravanaDTO) {
        if (caravanaDTO == null) {
            return null;
        }
        Caravana caravana = new Caravana();
        caravana.setId(caravanaDTO.getId());
        caravana.setNombre(caravanaDTO.getNombre());
        caravana.setVelocidad_base(caravanaDTO.getVelocidadBase());
        caravana.setVelocidad_actual(caravanaDTO.getVelocidadActual());
        caravana.setCapacidad_base(caravanaDTO.getCapacidadBase());
        caravana.setCapacidad_actual(caravanaDTO.getCapacidadActual());
        caravana.setDinero(caravanaDTO.getDinero());
        caravana.setSalud_actual(caravanaDTO.getSaludActual());
        caravana.setSalud_maxima(caravanaDTO.getSaludMaxima());
        caravana.setTiempo_acumulado(caravanaDTO.getTiempoAcumulado());
        caravana.setTiene_guardias(caravanaDTO.isTieneGuardias());
        return caravana;
    }
}