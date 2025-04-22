import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CiudadCaravanaService } from '../../ciudad/ciudad-caravana.service';
import { CiudadService } from '../../ciudad/ciudad.service';
import { CaravanaService } from '../../caravana/caravana.service';
import { PartidaDto } from '../../dto/partida-dto';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { RutaDto } from '../../dto/ruta-dto';
import { CiudadOrigenService } from '../../ciudad/ciudad-origen.service';
import { RutaService } from '../../ruta/ruta.service';
import { CiudadDestinoService } from '../../ciudad/ciudad-destino.service';
import { forkJoin, of } from 'rxjs';
import { PartidaCaravanaService } from '../../partida/caravana.service';
import { PartidaService } from '../../partida/partida.service';
import { ContadorService } from '../../contador/contador.service';
import { switchMap, map } from 'rxjs/operators';

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
    private ciudadEscogida!: CiudadDto;
    private partidaId!: number;
    private ciudadId!: number;
    private caravanaId!: number;
    private rutasIds!: number[];
    constructor(
      private route: ActivatedRoute,
      private partidaService: PartidaService,
      private ciudadCaravanaService: CiudadCaravanaService,
      private ciudadOrigenService: CiudadOrigenService,
      private ciudadDestinoService: CiudadDestinoService,
      private caravanaService: CaravanaService,
      private ciudadService: CiudadService,
      private rutaService: RutaService,
      private partidaCaravanaService: PartidaCaravanaService,
      private contadorService: ContadorService,
      private router: Router
    ) {}

  ngOnInit(): void {
    this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
    this.ciudadId = Number(this.route.snapshot.paramMap.get('idCiudad'));
    this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));

    this.partidaService.recuperarPartida(this.partidaId).subscribe(partida => {
      this.partida = partida;
    });
    this.caravanaService.recuperarCaravana(this.caravanaId).subscribe(caravana => {
      this.caravana = caravana;
    });

    let mensaje = 'Recolectaste un total de ' + this.caravana.dinero + ' monedas de oro.';
    if (this.caravana.dinero >= this.partida.gananciaMinima) {
      mensaje += ' ¡Felicidades, ganaste la partida!';
    } else {
      mensaje += ' Haz perdido la partida :(';
    }
  }
}
