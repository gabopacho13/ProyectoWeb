import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Inject } from '@angular/core';
import { PartidaDto } from '../../dto/partida-dto';
import { PartidaCaravanaDto } from '../../dto/partida-caravana-dto';
import { CaravanaJugadorDto } from '../../dto/caravana-jugador-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { JugadorDto } from '../../dto/jugador-dto';
import { CaravanaJugadorService } from '../../caravana/jugador.service';
import { CiudadCaravanaService } from '../../ciudad/ciudad-caravana.service'; 
import { CaravanaService } from '../../caravana/caravana.service';
import { JugadorService } from '../../jugador/jugador.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router'; 
import { switchMap } from 'rxjs/operators'; 
import { CiudadDto } from '../../dto/ciudad-dto';
import { CiudadService } from '../ciudad.service';
import { CaravanaCiudadDto } from '../../dto/caravana-ciudad-dto';
import { ServicioService } from '../../servicio/servicio.service';
import { ServicioDto } from '../../dto/servicio-dto';

@Component({
  selector: 'app-ciudad-actual',
  imports: [FormsModule, CommonModule],
  templateUrl: './ciudad-actual.component.html',
  styleUrl: './ciudad-actual.component.css'
})
export class CiudadActualComponent {
  public partida!: PartidaDto;
  public ciudad !: CiudadDto;
  public caravana !: CaravanaDto;
  public servicio !: ServicioDto;
  private partidaId!: number;
  private ciudadId!: number;
  private caravanaId!: number;
  private servicioId!: number;
  constructor(
    private route: ActivatedRoute,
    private caravanaService: CaravanaService,
    private ciudadService: CiudadService,
    private servicioService: ServicioService,
    private router: Router
  ) {} 

  ngOnInit(): void {
    this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
    this.ciudadId = Number(this.route.snapshot.paramMap.get('idCiudad'));
    this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));
    this.servicioId = Number(this.route.snapshot.paramMap.get('idServicio'));

    this.caravanaService.recuperarCaravana(this.caravanaId).subscribe(caravana => {
      this.caravana = caravana;
    }
    );
    this.ciudadService.recuperarCiudad(this.ciudadId).subscribe(ciudad => {
      this.ciudad = ciudad;
    }
    );
    this.servicioService.recuperarServicio(this.servicioId).subscribe(servicio => {
      this.servicio = servicio;
    }
    );
  }

  viajar(): void {
    this.router.navigate([`/partida/${this.partidaId}/ciudad/${this.ciudadId}/caravana/${this.caravanaId}/viajar`]);
  }

  Productos(): void {
    this.router.navigate([`producto/ciudad/${this.ciudadId}`]);
  }

  servicios(): void {
    this.router.navigate([`servicio/ciudad/${this.ciudadId}`]);
  }
}
