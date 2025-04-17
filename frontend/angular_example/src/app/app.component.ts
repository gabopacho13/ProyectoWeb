import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CiudadListaComponent } from "./ciudad/ciudad-lista/ciudad-lista.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CiudadListaComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'angular_example';
  cambiarTexto() {
    this.title = "cambiazo"
  }
}
