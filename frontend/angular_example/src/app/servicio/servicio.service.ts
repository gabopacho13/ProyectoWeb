import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ServicioDto } from '../dto/servicio-dto';
import { Observable } from 'rxjs';
import { ServicioCiudadDto } from '../dto/servicio-ciudad-dto';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {
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

  recuperarServicio(id: number): Observable<ServicioDto>{
    return this.http.get<ServicioDto>(`http://localhost:8080/servicio/${id}`);
  }

  listarServicios():Observable<ServicioDto[]>{
    return this.http.get<ServicioDto[]>(`http://localhost:8080/servicio/lista`);
  }

  listarServiciosCiudad(id: number): Observable<ServicioCiudadDto[]>{
    return this.http.get<ServicioCiudadDto[]>(`http://localhost:8080/ciudad/servicios/${id}`);
  }
}
