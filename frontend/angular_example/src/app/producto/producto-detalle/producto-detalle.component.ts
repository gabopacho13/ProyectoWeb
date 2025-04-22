import { ProductoService } from './../producto.service';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProductoDto } from '../../dto/producto-dto';
import { FormsModule } from '@angular/forms';
import { CompraventaService } from '../../servicio/compraventa.service';

@Component({
  selector: 'app-producto-detalle',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './producto-detalle.component.html',
  styleUrl: './producto-detalle.component.css'
})
export class ProductoDetalleComponent {

  producto: ProductoDto | undefined;
  cantidad: number = 1;

  constructor(
    private productoService: ProductoService,
    private compraventaService: CompraventaService
  ) {}

  @Input()
  set id(id: number) {
    this.productoService.recuperarProducto(id)
      .subscribe(p => this.producto = p);
  }

  comprar() {
    if (!this.producto) return;
    this.compraventaService.comprar(1, 1, this.producto.id, this.cantidad)
      .subscribe(() => alert('Compra realizada'));
  }

  vender() {
    if (!this.producto) return;
    this.compraventaService.vender(1, 1, this.producto.id, this.cantidad)
      .subscribe(() => alert('Venta realizada'));
  }
}