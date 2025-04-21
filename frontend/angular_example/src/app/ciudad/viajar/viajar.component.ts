import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CiudadCaravanaService } from '../ciudad-caravana.service';
import { CiudadService } from '../ciudad.service';
import { CaravanaService } from '../../caravana/caravana.service';
import { PartidaDto } from '../../dto/partida-dto';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { RutaDto } from '../../dto/ruta-dto';

@Component({
  selector: 'app-viajar',
  imports: [],
  templateUrl: './viajar.component.html',
  styleUrl: './viajar.component.css'
})
export class ViajarComponent {
  public partida!: PartidaDto;
  public ciudad !: CiudadDto;
  public caravana !: CaravanaDto;
  public rutas !: RutaDto[];
  private partidaId!: number;
  private ciudadId!: number;
  private caravanaId!: number;
  constructor(
    private route: ActivatedRoute,
    private ciudadCaravanaService: CiudadCaravanaService,
    private caravanaService: CaravanaService,
    private ciudadService: CiudadService,
    private router: Router
  ) {} 

  ngOnInit(): void {
    this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
    this.ciudadId = Number(this.route.snapshot.paramMap.get('idCiudad'));
    this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));

    this.caravanaService.recuperarCaravana(this.caravanaId).subscribe(caravana => {
      this.caravana = caravana;
    }
    );
    this.ciudadService.recuperarCiudad(this.ciudadId).subscribe(ciudad => {
      this.ciudad = ciudad;
    }
    );
  }

}
