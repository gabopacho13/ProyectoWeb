import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CiudadDto } from '../dto/ciudad-dto';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

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
}
