import { CaravanaDto } from './../dto/caravana-dto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CaravanaService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/caravana'; 

  constructor(private http: HttpClient) { }

  listarCaravanas(): Observable<CaravanaDto[]> {
    return this.http.get<CaravanaDto[]>(`${this.apiUrl}/lista`);
  }

  recuperarCaravana(idCaravana: number): Observable<CaravanaDto> {
    return this.http.get<CaravanaDto>(`${this.apiUrl}/${idCaravana}`);
  }

  crearCaravana(caravana: CaravanaDto): Observable<CaravanaDto> {
    return this.http.post<CaravanaDto>(
      this.apiUrl,
      caravana,
      this.httpOptions
    ).pipe(
      tap(nuevaCaravana => {
        console.log("Caravana creada en backend:", nuevaCaravana);
      })
    );
  }

  actualizarCaravana(caravana: CaravanaDto): Observable<CaravanaDto> {
    return this.http.put<CaravanaDto>(
      `${this.apiUrl}`,
      caravana,
      this.httpOptions
    ).pipe(
      tap(caravanaActualizada => {
        console.log("Caravana actualizada en backend:", caravanaActualizada);
      })
    );
  }

  eliminarCaravana(idCaravana: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idCaravana}`).pipe(
      tap(() => {
        console.log(`Caravana con ID ${idCaravana} eliminada en backend`);
      })
    );
  }
}