import { ServicioService } from './../servicio.service';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ServicioDto } from '../../dto/servicio-dto';

@Component({
  selector: 'app-servicio-view',
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './servicio-view.component.html',
  styleUrl: './servicio-view.component.css'
})
export class ServicioViewComponent {
  servicio : ServicioDto | undefined;
  constructor(
    private servicioService : ServicioService
  ){}

  @Input()
  set id(id:number){
    this.servicioService.recuperarServicio(id)
    .subscribe(
      servicio => this.servicio = servicio
    )
  }

  comprarServicio() {

  }
}
