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
import { CiudadOrigenService } from '../ciudad-origen.service';
import { RutaService } from '../../ruta/ruta.service';
import { CiudadDestinoService } from '../ciudad-destino.service';
import { forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-viajar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './viajar.component.html',
  styleUrl: './viajar.component.css'
})
export class ViajarComponent {
  public partida!: PartidaDto;
  public ciudad !: CiudadDto;
  public caravana !: CaravanaDto;
  public rutas !: RutaDto[];
  public ciudadesConectadas!: CiudadDto[];
  public ciudadesConRuta: CiudadConRuta[] = [];
  private ciudadEscogida!: CiudadDto;
  private partidaId!: number;
  private ciudadId!: number;
  private caravanaId!: number;
  private rutasIds!: number[];
  constructor(
    private route: ActivatedRoute,
    private ciudadCaravanaService: CiudadCaravanaService,
    private ciudadOrigenService: CiudadOrigenService,
    private ciudadDestinoService: CiudadDestinoService,
    private caravanaService: CaravanaService,
    private ciudadService: CiudadService,
    private rutaService: RutaService,
    private router: Router
  ) {} 

  ngOnInit(): void {
    this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
    this.ciudadId = Number(this.route.snapshot.paramMap.get('idCiudad'));
    this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));
  
    this.caravanaService.recuperarCaravana(this.caravanaId).subscribe(caravana => {
      this.caravana = caravana;
    });
  
    this.ciudadService.recuperarCiudad(this.ciudadId).subscribe(ciudad => {
      this.ciudad = ciudad;
    });
  
    forkJoin({
      rutasOrigen: this.ciudadOrigenService.getCiudadRutasOrigen(this.ciudadId),
      rutasDestino: this.ciudadDestinoService.getCiudadRutasDestino(this.ciudadId)
    }).subscribe(({ rutasOrigen, rutasDestino }) => {
      this.rutasIds = [...rutasOrigen.rutasOrigenIds, ...rutasDestino.rutasDestinoIds];
  
      for (let rutaId of this.rutasIds) {
        this.rutaService.obtenerRuta(rutaId).subscribe(ruta => {
          this.rutaService.obtenerConexiones(rutaId).subscribe(ciudades => {
            for (let ciudad of ciudades) {
              if (ciudad.id !== this.ciudadId) {
                this.ciudadesConRuta.push({ ciudad, ruta });
              }
            }
          });
        });
      }
    });
  }

    viajarA(ciudadId: number): void {
    console.log("Ciudad seleccionada:", ciudadId);
    
    this.ciudadService.recuperarCiudad(ciudadId).subscribe(ciudad => {
      this.ciudadEscogida = ciudad;
      console.log("Ciudad escogida:", this.ciudadEscogida);
      
      if (!this.ciudadEscogida) {
        alert("Error al recuperar la ciudad.");
        return;
      }
  
      if (this.caravana.dinero.valueOf() < this.ciudadEscogida.impuesto_entrada.valueOf()) {
        alert("No tienes suficiente dinero para viajar a esta ciudad");
        return;
      }
  
      this.caravana.dinero -= this.ciudadEscogida.impuesto_entrada.valueOf();
      this.caravanaService.actualizarCaravana(this.caravana).subscribe(() => {
        console.log("Caravana actualizada:", this.caravana);
      });
  
      this.ciudadCaravanaService.recuperarCaravanaCiudad(ciudadId).subscribe(caravanaCiudad => {
        caravanaCiudad.caravanasIds.push(this.caravanaId);
        this.ciudadCaravanaService.actualizarCaravanaCiudad(ciudadId, caravanaCiudad).subscribe(() => {
          console.log("Caravana ciudad actualizada:", caravanaCiudad);
        });
      });
  
      this.router.navigate([
        '/partida',
        this.partidaId,
        'ciudad',
        ciudadId,
        'caravana',
        this.caravanaId
      ]);
    });
  }
}  

interface CiudadConRuta {
  ciudad: CiudadDto;
  ruta: RutaDto;
}
