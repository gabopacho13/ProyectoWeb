import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription, switchMap, of, map, forkJoin } from 'rxjs';
import { catchError } from 'rxjs/operators';

// DTOs
import { ServicioCiudadDto } from '../../dto/servicio-ciudad-dto';
import { ServiciosCompradosDto } from '../../dto/servicios-comprados-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { ServicioDto } from '../../dto/servicio-dto';

// Services
import { ServicioService } from './../servicio.service';
import { CaravanaService } from '../../caravana/caravana.service';

@Component({
  selector: 'app-servicio-comercio',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './servicio-comercio.component.html',
  styleUrls: ['./servicio-comercio.component.css']
})
export class ServicioComercioComponent implements OnInit, OnDestroy {

  public serviciosCiudad: ServicioCiudadDto[] = [];
  private ciudadId: number = 0;
  private caravanaId: number = 0;
  private routeSubscription: Subscription | null = null;
  // Para rastrear compras SÓLO durante esta instancia del componente
  private serviciosCompradosEnEstaSesion: Set<number> = new Set();

  constructor(
    private route: ActivatedRoute,
    private servicioService: ServicioService,
    private caravanaService: CaravanaService
  ) { }

  ngOnInit(): void {
    this.routeSubscription = this.route.paramMap.subscribe(params => {
      const ciudadIdFromRoute = params.get('idCiudad');
      const carId = params.get('idCaravana');
      const prevCiudadId = this.ciudadId; // Guardar ID anterior

      this.ciudadId = ciudadIdFromRoute ? Number(ciudadIdFromRoute) : 0;
      this.caravanaId = carId ? Number(carId) : 0;

      console.log(`Contexto leído de la ruta: CiudadID=${this.ciudadId}, CaravanaID=${this.caravanaId}`);

      if (this.caravanaId <= 0 || this.ciudadId <= 0) {
        console.error("Error: IDs inválidos de Caravana o Ciudad.");
        alert("Error: No se pudo cargar la información necesaria para esta vista.");
      } else {
        // Cargar servicios SIEMPRE que los IDs sean válidos
        this.cargarServiciosCiudad();
      }
    });
  }

  verDetalle(id: number) {
    console.log('Servicio a ver:', id);
  }

  cargarServiciosCiudad(): void {
    console.log(`Intentando cargar servicios para Ciudad ID ${this.ciudadId}`);
    // Limpiar el set de compras locales SIEMPRE que cargamos servicios para una ciudad
    this.serviciosCompradosEnEstaSesion.clear();
    console.log('Set de compras locales limpiado.');

    if (this.ciudadId > 0) {
      this.servicioService.listarServiciosCiudad(this.ciudadId)
        .subscribe({
          next: servicios => {
            this.serviciosCiudad = servicios;
            console.log(`Servicios cargados para Ciudad ID ${this.ciudadId}:`, servicios);
          },
          error: err => console.error(`Error al cargar servicios para Ciudad ID ${this.ciudadId}:`, err)
        });
    } else {
      console.warn("No se cargaron servicios porque ciudadId no es válido.");
    }
  }

  ngOnDestroy(): void {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  comprarServicio(servicioIdAComprar: number): void {
    console.log(`DEBUG: Iniciando comprarServicio FE WORKAROUND. ServicioID=${servicioIdAComprar}. CaravanaID=${this.caravanaId}, CiudadID=${this.ciudadId}`);


    if (this.caravanaId <= 0 || this.ciudadId <= 0) {
      alert("Error: No se puede realizar la compra. Falta información de contexto.");
      return;
    }

    if (this.serviciosCompradosEnEstaSesion.has(servicioIdAComprar)) {
      console.warn(`Intento de comprar Servicio ID ${servicioIdAComprar} que ya fue comprado en esta sesión/ciudad.`);
      alert("Ya has comprado este servicio en esta ciudad durante esta visita.");
      return;
    }

    const servicioCiudad = this.serviciosCiudad.find(sc => sc.idServicio === servicioIdAComprar);
    if (!servicioCiudad) {
      alert("Error: Servicio no encontrado para esta ciudad.");
      return;
    }
    const precioServicio = servicioCiudad.precio;

    forkJoin({
      caravana: this.caravanaService.recuperarCaravana(this.caravanaId),
      servicio: this.servicioService.recuperarServicio(servicioIdAComprar)
    }).pipe(
      switchMap(({ caravana, servicio }) => {
        console.log("Caravana actual recuperada:", caravana);
        console.log("Definición del servicio recuperada:", servicio);

        if (caravana.dinero < precioServicio) {
          console.warn(`Dinero insuficiente: ${caravana.dinero} MO < ${precioServicio} MO`);
          alert("¡Dinero insuficiente para comprar este servicio!");
          return of(null); // Detener
        }

        if (servicio.id === 4 && caravana.tieneGuardias) {
          console.warn("Intento de comprar Guardias (ID 4) cuando ya se tienen.");
          alert("Ya tienes Guardias, no puedes comprarlos de nuevo.");
          return of(null); // Detener
        }

        const compraDto = new ServiciosCompradosDto(0, servicioIdAComprar, this.caravanaId, new Date().toISOString());
        return this.servicioService.crearServiciosComprados(compraDto).pipe(
          map(registro => ({ caravana, servicio, precioServicio, registroExitoso: true })),
          catchError(errRegistro => {
            console.warn("Fallo al registrar la compra (POST /caravana/servicios), continuando para actualizar caravana...", errRegistro);
            return of({ caravana, servicio, precioServicio, registroExitoso: false });
          })
        );
      }),
      switchMap((datos) => {
        if (!datos) return of(null);

        const { caravana, servicio, precioServicio } = datos;
        const caravanaModificada = JSON.parse(JSON.stringify(caravana)) as CaravanaDto;
        caravanaModificada.dinero -= precioServicio;

        switch (servicio.id) {
          case 1: // Reparar
            caravanaModificada.saludActual = caravanaModificada.saludMaxima; break;
          case 2: // Ampliar Bodega
            const capMax = caravana.capacidadBase * 5.0;
            const capInc = caravana.capacidadBase * 0.20;
            caravanaModificada.capacidadActual = Math.round(Math.min(caravana.capacidadActual + capInc, capMax)); break;
          case 3: // Mejorar Ejes
            const velMax = caravana.velocidadBase * 1.5;
            const velInc = caravana.velocidadBase * 0.10;
            caravanaModificada.velocidadActual = Math.round(Math.min(caravana.velocidadActual + velInc, velMax) * 10) / 10; break;
          case 4: // Contratar Guardias
            caravanaModificada.tieneGuardias = true; break;
          default: console.warn(`Servicio ID ${servicio.id} sin efecto FE.`);
        }

        if (caravanaModificada.saludActual > caravanaModificada.saludMaxima) {
          caravanaModificada.saludActual = caravanaModificada.saludMaxima;
        }

        console.log("Enviando caravana actualizada (PUT):", caravanaModificada);
        // Enviar el estado calculado al backend usando PUT /caravana
        return this.caravanaService.actualizarCaravana(caravanaModificada);
      })
    ).subscribe({
      next: (caravanaFinalActualizada: CaravanaDto | null) => {
        if (caravanaFinalActualizada) {
          console.log("Caravana actualizada en backend via PUT:", caravanaFinalActualizada);
          alert("¡Servicio comprado y caravana actualizada!");
          this.serviciosCompradosEnEstaSesion.add(servicioIdAComprar);
          console.log('Servicio ID ${servicioIdAComprar} añadido a compras locales.');
        } else {
          console.log("Proceso de compra detenido por validación previa.");
        }
      },
      error: (err) => {
        console.error("Error final en el proceso de compra (workaround frontend):", err);
      }
    });
  }
  yaCompradoLocalmente(servicioId: number): boolean {
    return this.serviciosCompradosEnEstaSesion.has(servicioId);
  }
}