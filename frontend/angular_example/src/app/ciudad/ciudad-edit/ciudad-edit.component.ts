import { Component, Input } from '@angular/core';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CiudadService } from '../ciudad.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ciudad-edit',
  imports: [FormsModule],
  templateUrl: './ciudad-edit.component.html',
  styleUrl: './ciudad-edit.component.css'
})
export class CiudadEditComponent {
  ciudad: CiudadDto | undefined;

  constructor(
    private ciudadService : CiudadService,
    private router : Router
  ){}

  @Input()
  set id(id:number){
    this.ciudadService.recuperarCiudad(id).subscribe(ciudad => this.ciudad = ciudad);
  }

  onSubmit(){
    if(this.ciudad){
      this.ciudadService.modificarCiudad(this.ciudad)
    .subscribe(
      nuevaCiudad =>{
        console.log("ciudad editada", nuevaCiudad);
        this.router.navigate(['ciudad/lista']);
      }
    )
    }
  }
}
