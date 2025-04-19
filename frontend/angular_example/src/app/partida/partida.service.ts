import { PartidaDto } from './../dto/partida-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
    private http: HttpClient
  ) { }

  crearPartida(partida: PartidaDto): Observable<PartidaDto>{
    return this.http.post<PartidaDto>(
      `http://localhost:8080/partida`,
      partida,
      this.httpOptions
    );
  }
}
