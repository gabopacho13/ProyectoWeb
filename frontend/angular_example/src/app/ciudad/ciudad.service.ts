import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CiudadDto } from '../dto/ciudad-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CiudadService {

  constructor(
    private http: HttpClient
  ) { }

  listarCiudades() : Observable<CiudadDto[]>{
    return this.http.get<CiudadDto[]>("http://localhost:8080/ciudad/lista")
  }
}
