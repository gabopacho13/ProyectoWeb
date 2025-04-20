import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PartidaDto } from '../../dto/partida-dto';
import { PartidaService } from '../partida.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; 

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
    tiempoLimite: 15,
    gananciaMinima: 100,
    tiempoInicio: getLocalDateTimeString(),
    tiempoActual: 0
  };

  constructor(
    private partidaService: PartidaService,
    private router: Router 
  ) {}

  onSubmit() {
    if (this.partida && this.partida.tiempoLimite > 0) {
      this.partidaService.crearPartida(this.partida)
        .subscribe({
          next: nuevaPartida => {
            console.log("partida creada, respuesta recibida en componente:", nuevaPartida);
            this.router.navigate(['/ciudad/lista']);
          },
          error: err => {
            console.error("Error al crear la partida:", err);
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