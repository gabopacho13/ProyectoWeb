package co.edu.javeriana.jpa_example2.mapper;


import co.edu.javeriana.jpa_example2.dto.JugadorDTO;
import co.edu.javeriana.jpa_example2.model.Jugador;


public class JugadorMapper {
    public static JugadorDTO toDTO(Jugador jugador){
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setId(jugador.getId());
        jugadorDTO.setNombre(jugador.getNombre());
        jugadorDTO.setRol(jugador.getRol());
        jugadorDTO.setCaravana(jugador.getCaravana());

        return jugadorDTO;
    }
    public static Jugador toEntity(JugadorDTO jugadorDTO){
        Jugador jugador = new Jugador();
        jugador.setId(jugadorDTO.getId());
        jugador.setNombre(jugadorDTO.getNombre());
        jugador.setRol(jugadorDTO.getRol());
        jugador.setCaravana(jugadorDTO.getCaravana());
        
        return jugador;
    }
}
