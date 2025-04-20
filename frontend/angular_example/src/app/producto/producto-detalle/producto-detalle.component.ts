import { ProductoService } from './../producto.service';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProductoDto } from '../../dto/producto-dto';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-producto-detalle',
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './producto-detalle.component.html',
  styleUrl: './producto-detalle.component.css'
})
export class ProductoDetalleComponent {
  
  parametroProducto : ProductoDto | undefined
  constructor(
    private productoService : ProductoService
  ){}

  @Input()
  set id(id:number){
    this.productoService.recuperarProducto(id)
    .subscribe(
      producto => this.parametroProducto = producto
    )
  }

  comprarProducto() {

  }
}
