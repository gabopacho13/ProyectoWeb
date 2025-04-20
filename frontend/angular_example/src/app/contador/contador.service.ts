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
    const ahoraEpoch = Date.now();
    this.tiempoFinalEpoch = ahoraEpoch + (this.tiempoLimiteInicialSegundos * 1000);

    this.actualizarTiempoRestante(); 

    this.intervalo = setInterval(() => {
      this.actualizarTiempoRestante();
    }, 1000);
  }

  private actualizarTiempoRestante() {
    if (this.tiempoFinalEpoch === null) {
      this.detenerContadorInterno(); 
      this.tiempoRestanteSource.next(null); 
      return;
    }

    const ahoraEpoch = Date.now();
    const restanteMs = this.tiempoFinalEpoch - ahoraEpoch;
    const restanteSegundos = Math.max(0, Math.floor(restanteMs / 1000));

    this.tiempoRestanteSource.next(restanteSegundos);

    if (restanteSegundos <= 0) {
      console.log('ContadorService: Tiempo finalizado');
      this.detenerContadorInterno(); 
      this.tiempoTerminadoSource.next();
    }
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