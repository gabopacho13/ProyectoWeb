import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { ContadorService } from '../contador.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contador-principal',
  imports: [CommonModule],
  templateUrl: './contador-principal.component.html',
  styleUrl: './contador-principal.component.css'
})
export class ContadorPrincipalComponent {
  tiempoRestante!: number;
  private subscription!: Subscription;
  intervalo: any;

  constructor(
    private contadorService: ContadorService
  ){}

  ngOnInit() {
    this.subscription = this.contadorService.tiempoLimite$.subscribe(tiempo => {
      this.tiempoRestante = tiempo * 60; // Convertir minutos a segundos
      this.iniciarContador();
    });
  }

  iniciarContador() {
    if (this.intervalo) clearInterval(this.intervalo);
    
    this.intervalo = setInterval(() => {
      if (this.tiempoRestante > 0) {
        this.tiempoRestante--;
      } else {
        clearInterval(this.intervalo);
      }
    }, 1000);
  }

  get minutos(): number {
    return Math.floor(this.tiempoRestante / 60);
  }

  get segundos(): number {
    return this.tiempoRestante % 60;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    clearInterval(this.intervalo);
  }
}
