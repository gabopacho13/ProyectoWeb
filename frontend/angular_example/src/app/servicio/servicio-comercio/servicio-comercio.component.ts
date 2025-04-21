import { Component, Input, OnInit } from '@angular/core'; 
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ServicioService } from './../servicio.service';
import { ServicioCiudadDto } from './../../dto/servicio-ciudad-dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicio-comercio',
  imports: [RouterModule, CommonModule],
  templateUrl: './servicio-comercio.component.html',
  styleUrl: './servicio-comercio.component.css'
})

export class ServicioComercioComponent{    
  public serviciosCiudad!: ServicioCiudadDto[]; 

  constructor(
    private route: ActivatedRoute,             
    private servicioService: ServicioService   
  ) { }

  @Input()
  set id(id:number){
    this.servicioService.listarServiciosCiudad(id)
    .subscribe(
      servicios => this.serviciosCiudad = servicios
    )
  }

}