import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CaravanaService } from '../../caravana/caravana.service';
import { PartidaDto } from '../../dto/partida-dto';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { RutaDto } from '../../dto/ruta-dto';
import { forkJoin, of } from 'rxjs';
import { PartidaService } from '../../partida/partida.service';

@Component({
  selector: 'app-fin-partida',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fin-partida.component.html',
  styleUrl: './fin-partida.component.css'
})
export class FinPartidaComponent {
  public partida!: PartidaDto;
    public ciudad !: CiudadDto;
    public caravana !: CaravanaDto;
    public rutas !: RutaDto[];
    public ciudadesConectadas!: CiudadDto[];
    public mensajeConfirmacion: string = '';
    public mostrarPopup: boolean = false;
    public ciudadSeleccionadaParaViajar: any = null;
    public mensaje: string = '';
    private partidaId!: number;
    private caravanaId!: number;
    constructor(
      private route: ActivatedRoute,
      private partidaService: PartidaService,
      private caravanaService: CaravanaService,
      private router: Router
    ) {}

    ngOnInit(): void {
      this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
      this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));
    
      forkJoin({
        partida: this.partidaService.recuperarPartida(this.partidaId),
        caravana: this.caravanaService.recuperarCaravana(this.caravanaId)
      }).subscribe(({ partida, caravana }) => {
        this.partida = partida;
        this.caravana = caravana;
    
        // Armamos el mensaje una vez tenemos todos los datos
        this.mensaje = `Recolectaste un total de ${caravana.dinero} monedas de oro.`;
        if (caravana.dinero >= partida.gananciaMinima) {
          this.mensaje += ' ¡Felicidades, ganaste la partida!';
        } else {
          this.mensaje += ' Has perdido la partida :(';
        }
      });
    }

    volverAlInicio(): void {
      this.router.navigate(['/inicio']); // Cambia la ruta si tu home es distinta
    }
}
