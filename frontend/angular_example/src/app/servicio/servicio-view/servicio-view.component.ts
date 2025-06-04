import { ServicioService } from './../servicio.service';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ServicioDto } from '../../dto/servicio-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-servicio-view',
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './servicio-view.component.html',
  styleUrl: './servicio-view.component.css'
})
export class ServicioViewComponent implements OnInit {
  servicio : ServicioDto | undefined;
  constructor(
    private route: ActivatedRoute,
    private servicioService: ServicioService
  ) {}



  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log("ID desde la ruta: ", id);
    this.servicioService.recuperarServicio(id).subscribe(
      servicio => this.servicio = servicio
    );
  }

  @Input()
  set id(id:number){
    console.log("ID recibido en ServicioViewComponent: ", id);
    this.servicioService.recuperarServicio(id)
    .subscribe(
      servicio => this.servicio = servicio
    )
  }  
}
