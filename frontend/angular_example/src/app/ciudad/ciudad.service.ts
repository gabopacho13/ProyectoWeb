import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CiudadDto } from '../dto/ciudad-dto';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { RutaDto } from '../dto/ruta-dto';
import { RutaDestinoDto } from '../dto/ruta-destino-dto';
import { RutaOrigenDto } from '../dto/ruta-origen-dto';

@Injectable({
  providedIn: 'root'
})
export class CiudadService {

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

  listarCiudades() : Observable<CiudadDto[]>{
    return this.http.get<CiudadDto[]>(`http://localhost:8080/ciudad/lista`);
  }

  recuperarCiudad(id: number): Observable<CiudadDto>{
    return this.http.get<CiudadDto>(`http://localhost:8080/ciudad/${id}`);
  }

  modificarCiudad(ciudad:CiudadDto): Observable<CiudadDto>{
    return this.http.put<CiudadDto>(
      `http://localhost:8080/ciudad`,
      ciudad,
      this.httpOptions
    )
  }

  listarRutas(): Observable<RutaDto[]>{
    return this.http.get<RutaDto[]>(`http://localhost:8080/ruta/lista`);
  }

  recuperarRuta(id: number): Observable<RutaDto>{
    return this.http.get<RutaDto>(`http://localhost:8080/ruta/${id}`);
  }

  listarRutasOrigen(): Observable<RutaOrigenDto[]>{
    return this.http.get<RutaOrigenDto[]>(`http://localhost:8080/ciudad/origen/rutas`);
  }

  recuperarRutaOrigen(id: number): Observable<RutaOrigenDto>{
    return this.http.get<RutaOrigenDto>(`http://localhost:8080/ciudad/origen/${id}`);
  }

  listarRutasDestino(): Observable<RutaDestinoDto[]>{
    return this.http.get<RutaDestinoDto[]>(`http://localhost:8080/ciudad/destino/rutas`);
  }

  recuperarRutaDestino(id: number): Observable<RutaDestinoDto>{
    return this.http.get<RutaDestinoDto>(`http://localhost:8080/ciudad/destino/${id}`);
  }

}
