import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RutaDto } from '../dto/ruta-dto';
import { CiudadDto } from '../dto/ciudad-dto';

@Injectable({
  providedIn: 'root'
})
export class RutaService {
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };
  private apiUrl = 'http://localhost:8080/ruta'; // Base URL del controlador

  constructor(private http: HttpClient) {}

  // Obtener la lista de todas las rutas
  listarRutas(): Observable<RutaDto[]> {
    return this.http.get<RutaDto[]>(`${this.apiUrl}/lista`);
  }

  // Obtener una ruta específica por su ID
  obtenerRuta(id: number): Observable<RutaDto> {
    return this.http.get<RutaDto>(`${this.apiUrl}/${id}`);
  }

  // Obtener las conexiones de una ruta específica
  obtenerConexiones(id: number): Observable<CiudadDto[]> {
    return this.http.get<CiudadDto[]>(`${this.apiUrl}/conexiones/${id}`);
  }

  // Crear una nueva ruta
  crearRuta(ruta: RutaDto): Observable<RutaDto> {
    return this.http.post<RutaDto>(
      `${this.apiUrl}/crear`,
      ruta,
      this.httpOptions
    );
  }

  // Actualizar una ruta existente
  actualizarRuta(id: number, ruta: RutaDto): Observable<RutaDto> {
    return this.http.put<RutaDto>(
      `${this.apiUrl}/actualizar/${id}`,
      ruta,
      this.httpOptions
    );
  }

  // Eliminar una ruta por su ID
  borrarRuta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/eliminar/${id}`);
  }
}
