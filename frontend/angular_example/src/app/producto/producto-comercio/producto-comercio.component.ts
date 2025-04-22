import { Component, Input } from '@angular/core';
import { ProductoService } from '../producto.service';
import { ProductoCiudadDto } from '../../dto/producto-ciudad-dto';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-producto-comercio',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './producto-comercio.component.html',
  styleUrl: './producto-comercio.component.css'
})
export class ProductoComercioComponent {
  parametroProductoCiudadLista: ProductoCiudadDto[] = [];

  constructor(
    private productoService: ProductoService,
    private router: Router
  ) {}

  @Input()
  set id(id: number) {
    this.productoService.ListaProductoPorCiudad(id)
      .subscribe(listaProductoCiudad => this.parametroProductoCiudadLista = listaProductoCiudad);
  }
}