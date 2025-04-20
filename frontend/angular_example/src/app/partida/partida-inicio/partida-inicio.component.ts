import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PartidaDto } from '../../dto/partida-dto';
import { PartidaService } from '../partida.service';
import { ContadorPrincipalComponent } from "../../contador/contador-principal/contador-principal.component";

@Component({
  selector: 'app-partida-inicio',
  imports: [FormsModule, ContadorPrincipalComponent],
  templateUrl: './partida-inicio.component.html',
  styleUrl: './partida-inicio.component.css'
})
export class PartidaInicioComponent {
  partida: PartidaDto = {
    id: 0,
    tiempoLimite: 0,
    gananciaMinima: 0,
    tiempoInicio: getLocalDateTimeString(),
    tiempoActual: 0
  };

  constructor(
    private partidaService: PartidaService,
  ){}

  onSubmit(){
    if(this.partida){
      this.partidaService.crearPartida(this.partida)
    .subscribe(
      nuevaPartida =>{
        console.log("partida creada", nuevaPartida);
      }
    )
    }
  } 
}

function getLocalDateTimeString(): string {
  const ahora = new Date();
  ahora.setMinutes(ahora.getMinutes() - ahora.getTimezoneOffset()); // ajusta a la zona local
  return ahora.toISOString().slice(0, 16); // formato compatible con datetime-local
}
