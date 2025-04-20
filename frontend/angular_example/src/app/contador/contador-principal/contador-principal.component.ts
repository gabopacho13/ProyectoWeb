import { Component, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContadorService } from '../contador.service';
import { Subscription } from 'rxjs'; // Necesario para limpiar suscripción manualmente

@Component({
  selector: 'app-contador-principal',
  standalone: true, 
  imports: [CommonModule], 
  templateUrl: './contador-principal.component.html',
  styleUrls: ['./contador-principal.component.css'], 
  changeDetection: ChangeDetectionStrategy.OnPush // Buena práctica con observables
})
export class ContadorPrincipalComponent implements OnDestroy {
  // Propiedad para almacenar el valor actual del observable
  tiempoRestante: number | null = null;
  private tiempoSubscription: Subscription;

  // Inyecta el servicio y ChangeDetectorRef
  constructor(
    private contadorService: ContadorService,
    private cdRef: ChangeDetectorRef // Para actualizar la vista cuando cambia el tiempo
  ) {
    this.tiempoSubscription = this.contadorService.tiempoRestante$.subscribe(tiempo => {
      this.tiempoRestante = tiempo;
      this.cdRef.markForCheck();
    });
  }

  // Getters para formatear el tiempo (manejan el caso null)
  get minutos(): number {
    return this.tiempoRestante !== null ? Math.floor(this.tiempoRestante / 60) : 0;
  }

  get segundos(): number {
    return this.tiempoRestante !== null ? this.tiempoRestante % 60 : 0;
  }

  // Devuelve true si el contador está activo 
  get contadorActivo(): boolean {
      return this.tiempoRestante !== null && this.tiempoRestante > 0;
  }

  ngOnDestroy() {
    // Limpia la suscripción cuando el componente se destruye
    if (this.tiempoSubscription) {
      this.tiempoSubscription.unsubscribe();
    }
  }
}