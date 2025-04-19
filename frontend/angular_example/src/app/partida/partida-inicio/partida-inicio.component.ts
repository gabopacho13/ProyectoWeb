import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PartidaDto } from '../../dto/partida-dto';
import { PartidaService } from '../partida.service';

@Component({
  selector: 'app-partida-inicio',
  imports: [FormsModule],
  templateUrl: './partida-inicio.component.html',
  styleUrl: './partida-inicio.component.css'
})
export class PartidaInicioComponent {
  partida: PartidaDto = {
    id: 0,
    tiempoLimite: 0,
    gananciaMinima: 0,
    tiempoInicio: '',  // Usa string para datetime (formato ISO)
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
        console.log("partida credaa", nuevaPartida);
      }
    )
    }
  }
}
