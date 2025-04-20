import { Component, OnInit, OnDestroy } from '@angular/core'; 
import { Subscription } from 'rxjs'; 
import { ContadorService } from './contador/contador.service'; 
import { CommonModule } from '@angular/common';
import { ContadorPrincipalComponent } from "./contador/contador-principal/contador-principal.component"; 
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [CommonModule, ContadorPrincipalComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'tu-proyecto';

  private tiempoTerminadoSubscription: Subscription | null = null;

  constructor(private contadorService: ContadorService) {}

  ngOnInit(): void {
    this.tiempoTerminadoSubscription = this.contadorService.tiempoTerminado$.subscribe(() => {

      alert('¡Se acabó el tiempo! Has perdido.');
    });
  }

  ngOnDestroy(): void {
    if (this.tiempoTerminadoSubscription) {
      this.tiempoTerminadoSubscription.unsubscribe();
    }
  }
}