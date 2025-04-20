import { PartidaCaravanaDto } from './../dto/partida-caravana-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ContadorService } from '../contador/contador.service';

@Injectable({
  providedIn: 'root'
})
export class PartidaCaravanaService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/partida/caravana'; 

  constructor(
    private http: HttpClient,
    private contadorService: ContadorService 
  ) { }

  recuperarPartidaCaravana(idPartida: number): Observable<PartidaCaravanaDto> {
    return this.http.get<PartidaCaravanaDto>(`${this.apiUrl}/${idPartida}`);
  }

  actualizarPartidaCaravana(idPartida: number, partidaCaravana: PartidaCaravanaDto): Observable<PartidaCaravanaDto> {
    return this.http.put<PartidaCaravanaDto>(
      `${this.apiUrl}/${idPartida}`,
      partidaCaravana,
      this.httpOptions
    ).pipe(
      tap(nuevaPartidaCaravana => {
        console.log("Partida Caravana actualizada en backend:", nuevaPartidaCaravana);
      })
    );
  }
}