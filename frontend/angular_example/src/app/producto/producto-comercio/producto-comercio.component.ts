import { Component, OnInit, OnDestroy } from '@angular/core'; 
import { ActivatedRoute, Router, RouterModule } from '@angular/router'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Subscription } from 'rxjs';

import { ProductoService } from './../producto.service'; 
import { ProductoCiudadDto } from '../../dto/producto-ciudad-dto'; 
import { InventarioCaravanaDto } from '../../dto/inventario-caravana-dto'; 

@Component({
  selector: 'app-producto-comercio',
  standalone: true, 
  imports: [
    RouterModule,
    CommonModule, 
    FormsModule   
  ],
  templateUrl: './producto-comercio.component.html',
  styleUrls: ['./producto-comercio.component.css']
})
export class ProductoComercioComponent implements OnInit, OnDestroy {

  parametroProductoCiudadLista: ProductoCiudadDto[] = [];
  cantidades: { [productoCiudadId: number]: number } = {}; 

  ciudadActualId!: number; 
  caravanaId!: number;     
  private routeSubscription: Subscription | undefined;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Suscribirse a los cambios en los parámetros de la ruta
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const caravanaIdFromRoute = params.get('idCaravana'); 
      const ciudadIdFromRoute = params.get('idCiudad');     

      if (caravanaIdFromRoute && ciudadIdFromRoute) {
        this.caravanaId = +caravanaIdFromRoute;
        this.ciudadActualId = +ciudadIdFromRoute;

        console.log("IDs obtenidos de la ruta -> Caravana:", this.caravanaId, "Ciudad:", this.ciudadActualId);

        this.refrescarListaProductos();

      } else {
        console.error("Error: IDs de caravana o ciudad no encontrados en los parámetros de la ruta.");
      }
    });
  }

  ngOnDestroy(): void {
    // Desuscribirse para evitar fugas de memoria cuando el componente se destruya
    this.routeSubscription?.unsubscribe();
  }

  refrescarListaProductos(): void {
    // Verificar que tenemos un ID de ciudad válido
    if (!this.ciudadActualId) {
      console.warn("Intentando refrescar productos sin ID de ciudad válido.");
      return;
    }
    this.productoService.ListaProductoPorCiudad(this.ciudadActualId)
      .subscribe(
        listaProductoCiudad => {
          this.parametroProductoCiudadLista = listaProductoCiudad;
          // Reinicializar las cantidades para los productos cargados
          this.cantidades = {}; // Limpiar mapa de cantidades
          this.parametroProductoCiudadLista.forEach(p => {
             // Usar el ID único de la relación ProductoCiudad como clave
            this.cantidades[p.id] = 1; // Por defecto, cantidad 1
          });
        },
        error => {
          console.error("Error al cargar productos de la ciudad:", error);
          this.parametroProductoCiudadLista = []; // Limpiar lista en caso de error
          alert("No se pudieron cargar los productos de la ciudad."); 
        }
      );
  }

  calcularPrecioCompra(producto: ProductoCiudadDto): number {
    if (!producto || (1 + producto.stock) <= 0) return 0; // Evitar división por cero o stock negativo 
    const precio = producto.factorOferta / (1.0 + producto.stock);
    return parseFloat(precio.toFixed(2));
  }

  calcularPrecioVenta(producto: ProductoCiudadDto): number {
    if (!producto || (1 + producto.stock) <= 0) return 0; // Evitar división por cero o stock negativo 
    const precio = producto.factorDemanda / (1.0 + producto.stock);
    return parseFloat(precio.toFixed(2));
  }
  // -----------------------------------

  comprar(producto: ProductoCiudadDto): void {
    const cantidad = this.cantidades[producto.id];
    // Validaciones básicas
    if (!this.validarEntradas(producto, cantidad)) return;

    console.log(`Intentando comprar ${cantidad} de producto ${producto.productoId} en ciudad ${this.ciudadActualId} para caravana ${this.caravanaId}`);

    this.productoService.comprarProducto(this.caravanaId, this.ciudadActualId, producto.productoId, cantidad)
      .subscribe({
        next: (inventarioActualizado) => {
          console.log("Compra exitosa:", inventarioActualizado);
          alert(`¡Compra exitosa! ${cantidad} unidades adquiridas.`);
          this.actualizarEstadoJuego(); // Actualizar la vista
        },
        error: (error) => this.manejarErrorTransaccion(error, 'compra')
      });
  }

  vender(producto: ProductoCiudadDto): void {
    const cantidad = this.cantidades[producto.id];
     // Validaciones básicas
    if (!this.validarEntradas(producto, cantidad)) return;

    console.log(`Intentando vender ${cantidad} de producto ${producto.productoId} en ciudad ${this.ciudadActualId} para caravana ${this.caravanaId}`);

     this.productoService.venderProducto(this.caravanaId, this.ciudadActualId, producto.productoId, cantidad)
      .subscribe({
        next: (inventarioActualizado) => {
           console.log("Venta exitosa:", inventarioActualizado);
           alert(`¡Venta exitosa! ${cantidad} unidades vendidas.`);
           this.actualizarEstadoJuego(); // Actualizar la vista
        },
        error: (error) => this.manejarErrorTransaccion(error, 'venta')
      });
  }

  // Método auxiliar para validaciones comunes
  validarEntradas(producto: ProductoCiudadDto, cantidad: number): boolean {
     if (!this.caravanaId || !this.ciudadActualId) {
       alert("Error: Falta información esencial de la caravana o ciudad.");
       return false;
     }
     if (!cantidad || cantidad <= 0) {
      alert("Por favor, introduce una cantidad válida (mayor que cero).");
      return false;
    }
    return true;
  }

  // Método auxiliar para manejar errores de API
  manejarErrorTransaccion(error: any, tipo: 'compra' | 'venta'): void {
     console.error(`Error en la ${tipo}:`, error);
     // Intenta obtener un mensaje de error significativo del backend
     const mensajeError = error?.error?.message || error?.message || `Error desconocido durante la ${tipo}.`;
     alert(`Error en la ${tipo}: ${mensajeError}`);
  }


  actualizarEstadoJuego(): void {
      this.refrescarListaProductos();
      console.log("Actualizando estado del juego...");
      
  }

}