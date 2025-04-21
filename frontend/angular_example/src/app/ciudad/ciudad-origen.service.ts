import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RutaOrigenDto} from '../dto/ruta-origen-dto'; 

@Injectable({
  providedIn: 'root'
})
export class CiudadOrigenService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/ciudad/origen'; // Base URL del controlador

  constructor(private http: HttpClient) {}

  // Obtener las rutas de origen de una ciudad específica
  getCiudadRutasOrigen(ciudadId: number): Observable<RutaOrigenDto> {
    return this.http.get<RutaOrigenDto>(`${this.apiUrl}/${ciudadId}`);
  }

  // Actualizar las rutas de origen de una ciudad
  updateCiudadRutasOrigen(ciudadRutasOrigenDto: RutaOrigenDto): Observable<RutaOrigenDto> {
    return this.http.put<RutaOrigenDto>(
      `${this.apiUrl}/actualizar`,
      ciudadRutasOrigenDto,
      this.httpOptions
    );
  }
}
