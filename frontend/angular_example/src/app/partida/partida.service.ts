import { PartidaDto } from './../dto/partida-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ContadorService } from '../contador/contador.service'; // Verifica la ruta
import { CaravanaDto } from '../dto/caravana-dto';

@Injectable({
  providedIn: 'root'
})
export class PartidaService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/partida'; 

  constructor(
    private http: HttpClient,
    private contadorService: ContadorService 
  ) { }

  listarPartida(): Observable<PartidaDto[]> {
    return this.http.get<PartidaDto[]>(`${this.apiUrl}/lista`);
  }

  recuperarPartida(id: number): Observable<PartidaDto> {
    return this.http.get<PartidaDto>(`${this.apiUrl}/${id}`);
  }

  crearPartida(partida: PartidaDto): Observable<PartidaDto> {
    return this.http.post<PartidaDto>(
      this.apiUrl, // Usar URL base
      partida,
      this.httpOptions
    ).pipe(
      tap(nuevaPartida => {
        console.log("Partida creada en backend:", nuevaPartida);
        if (nuevaPartida && typeof nuevaPartida.tiempoLimite === 'number') {
          this.contadorService.iniciarContador(nuevaPartida.tiempoLimite);
        } else {
          console.error("No se pudo iniciar el contador: tiempoLimite inválido en la respuesta", nuevaPartida);
        }
      })
    );
  }
}