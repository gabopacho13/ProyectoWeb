import { PartidaDto } from './../dto/partida-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ContadorService } from '../contador/contador.service';

@Injectable({
  providedIn: 'root'
})
export class PartidaService {
  private httpOptions ={
    headers: new HttpHeaders(
      {
        "Content-Type": "application/json"
      }
    )
  }

  constructor(
    private http: HttpClient,
    private contadorService: ContadorService
  ) { }

  listarPartida() : Observable<PartidaDto[]>{
    return this.http.get<PartidaDto[]>(`http://localhost:8080/partida/lista`);
  }

  recuperarPartida(id: number): Observable<PartidaDto>{
    return this.http.get<PartidaDto>(`http://localhost:8080/partida/${id}`);
  }

  crearPartida(partida: PartidaDto): Observable<PartidaDto>{
    return this.http.post<PartidaDto>(
      `http://localhost:8080/partida`,
      partida,
      this.httpOptions
    ).pipe(
      tap(nuevaPartida => {
        this.contadorService.setTiempoLimite(nuevaPartida.tiempoLimite);
      })
    );
  }
}
