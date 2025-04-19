import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { CiudadService } from '../ciudad.service';
import { CiudadDto } from '../../dto/ciudad-dto';
import { RutaDto } from '../../dto/ruta-dto';
import { RutaOrigenDto } from '../../dto/ruta-origen-dto';
import { RutaDestinoDto } from '../../dto/ruta-destino-dto';
import { RutaDefDto } from '../../dto/ruta-def-dto';

@Component({
  selector: 'app-ciudad-viaje',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ciudad-viaje.component.html',
  styleUrls: ['./ciudad-viaje.component.css']
})
export class CiudadViajeComponent {
  ciudad?: CiudadDto;
  rutas: RutaDto[] = [];
  origenRutas?: RutaOrigenDto;
  destinoRutas?: RutaDestinoDto;
  rutasDef: RutaDefDto[] = [];

  constructor(private ciudadService: CiudadService) {}

  @Input()
  set id(id: number) {
    // Carga ciudad, rutas y relaciones origen/destino en paralelo
    forkJoin({
      ciudad: this.ciudadService.recuperarCiudad(id),
      rutas: this.ciudadService.listarRutas(),
      origen: this.ciudadService.recuperarRutaOrigen(id),
      destino: this.ciudadService.recuperarRutaDestino(id)
    }).subscribe(({ ciudad, rutas, origen, destino }) => {
      this.ciudad = ciudad;
      this.rutas = rutas;
      this.origenRutas = origen;
      this.destinoRutas = destino;
      this.vincularRutasConOrigen();
    });
  }

  private vincularRutasConOrigen(): void {
    if (!this.origenRutas || !this.destinoRutas) {
      this.rutasDef = [];
      return;
    }
    // Asume que rutasOrigenIds y rutasDestinoIds están alineados en posición
    this.rutasDef = this.origenRutas.rutasOrigenIds.map((origId, index) => {
      const ruta = this.rutas.find(r => r.id === origId);
      const destId = this.destinoRutas!.rutasDestinoIds[index];
      return {
        id: ruta?.id ?? 0,
        dano: ruta?.dano ?? 0,
        distancia: ruta?.distancia ?? 0,
        es_segura: ruta?.es_segura ?? false,
        rutasOrigenIds: origId,
        rutasDestinoIds: destId
      } as RutaDefDto;
    });
  }
}
