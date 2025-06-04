import { JugadorDto } from './../dto/jugador-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JugadorService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/jugador'; 

  constructor(private http: HttpClient) { }

  listarJugadores(): Observable<JugadorDto[]> {
    return this.http.get<JugadorDto[]>(`${this.apiUrl}/lista`);
  }

  recuperarJugadorPorEmail(email: string): Observable<JugadorDto> {
    return this.http.get<JugadorDto>(`${this.apiUrl}/email/${email}`);
  } 

  recuperarJugador(id: number): Observable<JugadorDto> {
    return this.http.get<JugadorDto>(`${this.apiUrl}/${id}`);
  }

  crearJugador(jugador: JugadorDto): Observable<JugadorDto> {
    return this.http.post<JugadorDto>(
      this.apiUrl,
      jugador,
      this.httpOptions
    ).pipe(
      tap(nuevoJugador => {
        console.log("Jugador creado en backend:", nuevoJugador);
      })
    );
  }

  actualizarJugador(jugador: JugadorDto): Observable<JugadorDto> {
    return this.http.put<JugadorDto>(
      `${this.apiUrl}`,
      jugador,
      this.httpOptions
    ).pipe(
      tap(jugadorActualizado => {
        console.log("Jugador actualizado en backend:", jugadorActualizado);
      })
    );
  }

  eliminarJugador(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        console.log(`Jugador con ID ${id} eliminado en backend`);
      })
    );
  }
}