import { CiudadService } from './../ciudad.service';
import { Component, Input, input } from '@angular/core';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ciudad-view',
  imports: [CommonModule],
  templateUrl: './ciudad-view.component.html',
  styleUrl: './ciudad-view.component.css'
})
export class CiudadViewComponent {
  parametroCiudad: CiudadDto | undefined;

  constructor(
    private ciudadService : CiudadService,
    private router : Router
  ){}

  @Input()
  set id(id:number){
    this.ciudadService.recuperarCiudad(id).subscribe(ciudad => this.parametroCiudad = ciudad);
  }

  back(){
    this.router.navigate(['ciudad/lista']);
  }
}
