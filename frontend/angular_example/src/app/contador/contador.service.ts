import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContadorService {
  
  private tiempoRestanteSource = new BehaviorSubject<number | null>(null);
  tiempoRestante$: Observable<number | null> = this.tiempoRestanteSource.asObservable();
  private tiempoTerminadoSource = new Subject<void>();
  tiempoTerminado$: Observable<void> = this.tiempoTerminadoSource.asObservable();

  private intervalo: any = null;
  private tiempoFinalEpoch: number | null = null;
  private tiempoAcumulado: number = 0; // Variable para almacenar el tiempo acumulado

  constructor() {
  }

  iniciarContador(tiempoLimiteMinutos: number) {
    console.log('ContadorService: Iniciando contador con', tiempoLimiteMinutos, 'minutos');
    this.detenerContadorInterno(); 
  
    if (tiempoLimiteMinutos <= 0) {
      this.tiempoRestanteSource.next(null);
      this.tiempoFinalEpoch = null;
      return;
    }
  
    this.tiempoLimiteInicialSegundos = tiempoLimiteMinutos * 60;
  
    // Establecer el tiempo restante sin iniciar intervalo
    this.tiempoRestanteSource.next(this.tiempoLimiteInicialSegundos);
  }

  detenerContador() {
    console.log('ContadorService: Deteniendo contador explícitamente');
    this.detenerContadorInterno();
    this.tiempoRestanteSource.next(null);
    this.tiempoFinalEpoch = null;
  }

  private detenerContadorInterno() {
    if (this.intervalo) {
      clearInterval(this.intervalo);
      this.intervalo = null;
    }
  }
  
  private tiempoLimiteInicialSegundos: number = 0; 
}