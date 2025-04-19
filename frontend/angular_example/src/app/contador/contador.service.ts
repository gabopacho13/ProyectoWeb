import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContadorService {
  private tiempoLimiteSource = new BehaviorSubject<number>(0);
  tiempoLimite$ = this.tiempoLimiteSource.asObservable();

  setTiempoLimite(tiempo: number) {
    this.tiempoLimiteSource.next(tiempo);
  }
}
