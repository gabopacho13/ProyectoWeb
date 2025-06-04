import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PartidaDto } from '../../dto/partida-dto';
import { PartidaCaravanaDto } from '../../dto/partida-caravana-dto';
import { PartidaCaravanaService } from '../caravana.service';
import { CaravanaJugadorDto } from '../../dto/caravana-jugador-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { JugadorDto } from '../../dto/jugador-dto';
import { CaravanaJugadorService } from '../../caravana/jugador.service';
import { CiudadCaravanaService } from '../../ciudad/ciudad-caravana.service'; 
import { CaravanaService } from '../../caravana/caravana.service';
import { JugadorService } from '../../jugador/jugador.service';
import { PartidaService } from '../partida.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs/operators'; 

@Component({
  selector: 'app-partida-inicio',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './partida-inicio.component.html',
  styleUrls: ['./partida-inicio.component.css']
})
export class PartidaInicioComponent {
  partida: PartidaDto = {
    id: 0,
    tiempoLimite: 120,
    gananciaMinima: 4000,
    tiempoInicio: getLocalDateTimeString(),
    tiempoActual: 0
  };

  constructor(
    private partidaService: PartidaService,
    private partidaCaravanaService: PartidaCaravanaService,
    private caravanaJugadorService: CaravanaJugadorService,
    private caravanaService: CaravanaService,
    private jugadorService: JugadorService,
    private ciudadCaravanaService: CiudadCaravanaService,
    private router: Router,
  ) {}

  onSubmit() {
    if (this.partida && this.partida.tiempoLimite > 0) {
      let partidaId : number;
      let caravanaId : number;
      let ciudadId : number;
      const email = sessionStorage.getItem('user-email');
      this.partidaService.crearPartida(this.partida).pipe(
        switchMap(nuevaPartida => {
          console.log("Partida creada:", nuevaPartida);
          partidaId = nuevaPartida.id;
          const caravana = new CaravanaDto(0, getLocalDateTimeString(), "Caravana 1", 5, 40, 2000, 100, 0);
          return this.caravanaService.crearCaravana(caravana).pipe(
            switchMap(nuevaCaravana => {
              console.log("Caravana creada:", nuevaCaravana);
              caravanaId = nuevaCaravana.id;
              if (!email) {
                console.warn("No se encontró el email del jugador en sessionStorage");
                return [];
              }
              return this.jugadorService.recuperarJugadorPorEmail(email).pipe(
                switchMap(jugadorExistente => {
                  console.log("Jugador recuperado:", jugadorExistente);
                  const caravanaJugador = new CaravanaJugadorDto(nuevaCaravana.id, [jugadorExistente.id]);
                  return this.caravanaJugadorService.actualizarCaravanaJugador(jugadorExistente.id, caravanaJugador).pipe(
                    switchMap(() => {
                      const partidaCaravana = new PartidaCaravanaDto(nuevaPartida.id, [nuevaCaravana.id]);
                      return this.partidaCaravanaService.actualizarPartidaCaravana(nuevaPartida.id, partidaCaravana);
                    }),
                    switchMap(() => this.ciudadCaravanaService.recuperarCaravanaCiudad(1)),
                    switchMap(caravanaCiudad => {
                      caravanaCiudad.caravanasIds.push(nuevaCaravana.id);
                      ciudadId = caravanaCiudad.idCiudad;
                      return this.ciudadCaravanaService.actualizarCaravanaCiudad(1, caravanaCiudad);
                    })
                  );
                })
              );
            })
          );
        })
      ).subscribe({
        next: () => {
          console.log("Todo se creó correctamente. Redirigiendo...");
          this.router.navigate(['/partida', partidaId, 'ciudad', ciudadId, 'caravana', caravanaId]);
        },
        error: err => {
          console.error("Ocurrió un error en la cadena de creación:", err);
        }
      });
    } else {
      console.warn("El tiempo límite debe ser mayor a 0");
    }
  }
}

function getLocalDateTimeString(): string {
  const ahora = new Date();
  ahora.setMinutes(ahora.getMinutes() - ahora.getTimezoneOffset());
  return ahora.toISOString().slice(0, 16);
}