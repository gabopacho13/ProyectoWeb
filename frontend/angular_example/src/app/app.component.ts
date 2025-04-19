import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PartidaInicioComponent } from "./partida/partida-inicio/partida-inicio.component";
import { ContadorPrincipalComponent } from "./contador/contador-principal/contador-principal.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PartidaInicioComponent, ContadorPrincipalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'angular_example';
  cambiarTexto() {
    this.title = "cambiazo"
  }
}
