import { ProductoService } from './../producto.service';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProductoDto } from '../../dto/producto-dto';
import { FormsModule } from '@angular/forms';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-producto-detalle',
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './producto-detalle.component.html',
  styleUrl: './producto-detalle.component.css'
})
export class ProductoDetalleComponent implements OnInit {
  
  parametroProducto : ProductoDto | undefined
  constructor(
    private route: ActivatedRoute,
    private productoService : ProductoService
  ){}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log("ID desde la ruta: ", id);
    this.productoService.recuperarProducto(id).subscribe(
      producto => this.parametroProducto = producto
    );
  }

  @Input()
  set id(id:number){
    this.productoService.recuperarProducto(id)
    .subscribe(
      producto => this.parametroProducto = producto
    )
  }
}
