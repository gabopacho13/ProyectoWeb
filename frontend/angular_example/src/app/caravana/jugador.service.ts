import { CaravanaJugadorDto } from './../dto/caravana-jugador-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CaravanaJugadorService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/caravana/jugadores'; 

  constructor(private http: HttpClient) { }

  recuperarCaravanaJugador(idCaravana: number): Observable<CaravanaJugadorDto> {
    return this.http.get<CaravanaJugadorDto>(`${this.apiUrl}/${idCaravana}`);
  }

  actualizarCaravanaJugador(id: number, caravanaJugador: CaravanaJugadorDto): Observable<CaravanaJugadorDto> {
    return this.http.put<CaravanaJugadorDto>(
      `${this.apiUrl}/${id}`,
      caravanaJugador,
      this.httpOptions
    ).pipe(
      tap(caravanaJugadorActualizado => {
        console.log("Caravana Jugador actualizada en backend:", caravanaJugadorActualizado);
      })
    );
  }
}