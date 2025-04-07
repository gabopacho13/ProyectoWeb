package co.edu.javeriana.jpa_example2.mapper;

import co.edu.javeriana.jpa_example2.dto.PartidaDTO;
import co.edu.javeriana.jpa_example2.model.Partida;

public class PartidaMapper {

    // Convierte de entidad a DTO
    public static PartidaDTO toDTO(Partida partida) {
        if (partida == null) {
            return null;
        }
        return new PartidaDTO(
            partida.getId(),
            partida.getTiempoLimite(),
            partida.getGananciaMinima(),
            partida.getTiempoInicio(),
            partida.getTiempoActual()
        );
    }

    // Convierte de DTO a entidad
    public static Partida toEntity(PartidaDTO partidaDTO) {
        if (partidaDTO == null) {
            return null;
        }
        Partida partida = new Partida();
        partida.setTiempoLimite(partidaDTO.getTiempoLimite());
        partida.setGananciaMinima(partidaDTO.getGananciaMinima());
        partida.setTiempoInicio(partidaDTO.getTiempoInicio());
        partida.setTiempoActual(partidaDTO.getTiempoActual());
        return partida;
    }
}