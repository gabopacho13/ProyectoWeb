import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PartidaInicioComponent } from "./partida/partida-inicio/partida-inicio.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PartidaInicioComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'angular_example';
  cambiarTexto() {
    this.title = "cambiazo"
  }
}
