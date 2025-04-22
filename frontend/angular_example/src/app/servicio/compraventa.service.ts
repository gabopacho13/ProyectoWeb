import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ProductoCiudadDto } from "../dto/producto-ciudad-dto";

@Injectable({
  providedIn: 'root'
})
export class CompraventaService {

  private apiUrl = 'http://localhost:8080/api/compraventa';

  constructor(private http: HttpClient) {}

  obtenerPrecioCompra(ciudadId: number, productoId: number): Observable<ProductoCiudadDto> {
    return this.http.get<ProductoCiudadDto>(`${this.apiUrl}/precio-compra`, {
      params: new HttpParams()
        .set('ciudadId', ciudadId)
        .set('productoId', productoId)
    });
  }

  obtenerPrecioVenta(ciudadId: number, productoId: number): Observable<ProductoCiudadDto> {
    return this.http.get<ProductoCiudadDto>(`${this.apiUrl}/precio-venta`, {
      params: new HttpParams()
        .set('ciudadId', ciudadId)
        .set('productoId', productoId)
    });
  }

  comprar(caravanaId: number, ciudadId: number, productoId: number, cantidad: number) {
    return this.http.post(`${this.apiUrl}/comprar`, null, {
      params: new HttpParams()
        .set('caravanaId', caravanaId)
        .set('ciudadId', ciudadId)
        .set('productoId', productoId)
        .set('cantidad', cantidad)
    });
  }

  vender(caravanaId: number, ciudadId: number, productoId: number, cantidad: number) {
    return this.http.post(`${this.apiUrl}/vender`, null, {
      params: new HttpParams()
        .set('caravanaId', caravanaId)
        .set('ciudadId', ciudadId)
        .set('productoId', productoId)
        .set('cantidad', cantidad)
    });
  }
}