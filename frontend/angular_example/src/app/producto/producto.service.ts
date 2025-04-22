import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http'; 
import { Injectable } from '@angular/core';
import { ProductoCiudadDto } from '../dto/producto-ciudad-dto';
import { ProductoDto } from '../dto/producto-dto';
import { InventarioCaravanaDto } from '../dto/inventario-caravana-dto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private backendUrl = 'http://localhost:8080';

  private httpOptions = {
    headers: new HttpHeaders(
      {
        "Content-Type": "application/json"
      }
    )
  };

  constructor(
    private http: HttpClient
  ) { }

  ListaProductoPorCiudad(id: number): Observable<ProductoCiudadDto[]> {
    return this.http.get<ProductoCiudadDto[]>(`${this.backendUrl}/ciudad/producto/${id}`);
  }

  recuperarProducto(id: number): Observable<ProductoDto> {
    return this.http.get<ProductoDto>(`${this.backendUrl}/producto/${id}`);
  }

  comprarProducto(caravanaId: number, ciudadId: number, productoId: number, cantidad: number): Observable<InventarioCaravanaDto> {
    const url = `${this.backendUrl}/api/compraventa/comprar`;
    let params = new HttpParams()
      .set('caravanaId', caravanaId.toString())
      .set('ciudadId', ciudadId.toString())
      .set('productoId', productoId.toString())
      .set('cantidad', cantidad.toString());

    // POST, enviamos null como body porque los datos van en los params
    return this.http.post<InventarioCaravanaDto>(url, null, { params: params});
  }

  venderProducto(caravanaId: number, ciudadId: number, productoId: number, cantidad: number): Observable<InventarioCaravanaDto> {
    const url = `${this.backendUrl}/api/compraventa/vender`;
    let params = new HttpParams()
      .set('caravanaId', caravanaId.toString())
      .set('ciudadId', ciudadId.toString())
      .set('productoId', productoId.toString())
      .set('cantidad', cantidad.toString());
    return this.http.post<InventarioCaravanaDto>(url, null, { params: params });
  }
}