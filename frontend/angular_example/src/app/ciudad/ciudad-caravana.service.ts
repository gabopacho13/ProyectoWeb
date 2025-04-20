import { CaravanaCiudadDto } from './../dto/caravana-ciudad-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CiudadCaravanaService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/ciudad/caravanas'; 

  constructor(private http: HttpClient) { }

  recuperarCaravanaCiudad(id: number): Observable<CaravanaCiudadDto> {
    return this.http.get<CaravanaCiudadDto>(`${this.apiUrl}/${id}`);
  }

  actualizarCaravanaCiudad(id: number, caravanaCiudad: CaravanaCiudadDto): Observable<CaravanaCiudadDto> {
    return this.http.put<CaravanaCiudadDto>(
      `${this.apiUrl}/actualizar/${id}`,
      caravanaCiudad,
      this.httpOptions
    ).pipe(
      tap(caravanaCiudadActualizada => {
        console.log("Caravana Ciudad actualizada en backend:", caravanaCiudadActualizada);
      })
    );
  }
}
