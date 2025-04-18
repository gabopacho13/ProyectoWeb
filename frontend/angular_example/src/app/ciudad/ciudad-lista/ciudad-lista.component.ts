import { CommonModule } from '@angular/common';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CiudadService } from './../ciudad.service';
import { Component } from '@angular/core';
import { CiudadViewComponent } from "../ciudad-view/ciudad-view.component";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-ciudad-lista',
  imports: [CommonModule, RouterModule],
  templateUrl: './ciudad-lista.component.html',
  styleUrl: './ciudad-lista.component.css'
})
export class CiudadListaComponent {
    ciudades: CiudadDto[] = [];
    selectedCiudad: CiudadDto | undefined;

    constructor(
      private ciudadService : CiudadService
    ){ }

    ngOnInit(): void {
      this.ciudadService.listarCiudades()
      .subscribe(
        listaCiudades => this.ciudades = listaCiudades
      );
    }

    seleccionarCiudad(ciudadSeleccionada: CiudadDto) {
      this.selectedCiudad = ciudadSeleccionada;
    }  
}
