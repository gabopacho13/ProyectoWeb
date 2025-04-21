import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RutaDestinoDto } from '../dto/ruta-destino-dto'; 

@Injectable({
  providedIn: 'root'
})
export class CiudadDestinoService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/ciudad/destino'; // Base URL del controlador

  constructor(private http: HttpClient) {}

  // Obtener las rutas de destino de una ciudad específica
  getCiudadRutasDestino(ciudadId: number): Observable<RutaDestinoDto> {
    return this.http.get<RutaDestinoDto>(`${this.apiUrl}/${ciudadId}`);
  }

  // Actualizar las rutas de destino de una ciudad
  updateCiudadRutasDestino(ciudadRutasDestinoDto: RutaDestinoDto): Observable<RutaDestinoDto> {
    return this.http.put<RutaDestinoDto>(
      `${this.apiUrl}/actualizar`,
      ciudadRutasDestinoDto,
      this.httpOptions
    );
  }
}
