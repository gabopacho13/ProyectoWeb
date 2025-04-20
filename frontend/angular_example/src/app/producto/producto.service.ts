import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProductoCiudadDto } from '../dto/producto-ciudad-dto';
import { ProductoDto } from '../dto/producto-dto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

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

  ListaProductoPorCiudad(id: number): Observable<ProductoCiudadDto[]>{
    return this.http.
    get<ProductoCiudadDto[]>(`http://localhost:8080/ciudad/producto/${id}`);
  }

  recuperarProducto(id: number):Observable<ProductoDto>{
    return this.http.get<ProductoDto>(`http://localhost:8080/producto/${id}`);
  }
}
