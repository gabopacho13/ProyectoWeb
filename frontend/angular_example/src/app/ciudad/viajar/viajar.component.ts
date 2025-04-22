import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { CiudadCaravanaService } from '../ciudad-caravana.service';
import { CiudadService } from '../ciudad.service';
import { CaravanaService } from '../../caravana/caravana.service';
import { PartidaDto } from '../../dto/partida-dto';
import { CiudadDto } from '../../dto/ciudad-dto';
import { CaravanaDto } from '../../dto/caravana-dto';
import { RutaDto } from '../../dto/ruta-dto';
import { CiudadOrigenService } from '../ciudad-origen.service';
import { RutaService } from '../../ruta/ruta.service';
import { CiudadDestinoService } from '../ciudad-destino.service';
import { forkJoin, of } from 'rxjs';
import { PartidaCaravanaService } from '../../partida/caravana.service';
import { PartidaService } from '../../partida/partida.service';
import { ContadorService } from '../../contador/contador.service';
import { switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-viajar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './viajar.component.html',
  styleUrl: './viajar.component.css'
})
export class ViajarComponent {
  public partida!: PartidaDto;
  public ciudad !: CiudadDto;
  public caravana !: CaravanaDto;
  public rutas !: RutaDto[];
  public ciudadesConectadas!: CiudadDto[];
  public ciudadesConRuta: CiudadConRuta[] = [];
  public mensajeConfirmacion: string = '';
  public mostrarPopup: boolean = false;
  public ciudadSeleccionadaParaViajar: any = null;
  private ciudadEscogida!: CiudadDto;
  private partidaId!: number;
  private ciudadId!: number;
  private caravanaId!: number;
  private rutasIds!: number[];
  constructor(
    private route: ActivatedRoute,
    private partidaService: PartidaService,
    private ciudadCaravanaService: CiudadCaravanaService,
    private ciudadOrigenService: CiudadOrigenService,
    private ciudadDestinoService: CiudadDestinoService,
    private caravanaService: CaravanaService,
    private ciudadService: CiudadService,
    private rutaService: RutaService,
    private partidaCaravanaService: PartidaCaravanaService,
    private contadorService: ContadorService,
    private router: Router
  ) {} 

  ngOnInit(): void {
    this.partidaId = Number(this.route.snapshot.paramMap.get('idPartida'));
    this.ciudadId = Number(this.route.snapshot.paramMap.get('idCiudad'));
    this.caravanaId = Number(this.route.snapshot.paramMap.get('idCaravana'));
  
    forkJoin({
      caravana: this.caravanaService.recuperarCaravana(this.caravanaId),
      ciudad: this.ciudadService.recuperarCiudad(this.ciudadId),
      partida: this.partidaService.recuperarPartida(this.partidaId),
      rutasOrigen: this.ciudadOrigenService.getCiudadRutasOrigen(this.ciudadId),
      rutasDestino: this.ciudadDestinoService.getCiudadRutasDestino(this.ciudadId)
    }).subscribe(({ caravana, ciudad, partida, rutasOrigen, rutasDestino }) => {
      this.caravana = caravana;
      this.ciudad = ciudad;
      this.partida = partida;
      this.rutasIds = [...rutasOrigen.rutasOrigenIds, ...rutasDestino.rutasDestinoIds];
  
      let dineroSuficiente = false;
  
      const rutaObservables = this.rutasIds.map(rutaId =>
        this.rutaService.obtenerRuta(rutaId).pipe(
          switchMap(ruta =>
            this.rutaService.obtenerConexiones(rutaId).pipe(
              map(ciudades => ({ ruta, ciudades }))
            )
          )
        )
      );
  
      forkJoin(rutaObservables).subscribe(rutasConCiudades => {
        for (const item of rutasConCiudades) {
          const { ruta, ciudades } = item;
          for (const ciudad of ciudades) {
            if (ciudad.id !== this.ciudadId) {
              this.ciudadesConRuta.push({ ciudad, ruta });
              if (this.caravana.dinero.valueOf() >= ciudad.impuesto_entrada.valueOf()) {
                dineroSuficiente = true;
              }
            }
          }
        }
  
        if (!dineroSuficiente) {
          alert("No tienes suficiente dinero para viajar a ninguna ciudad.");
          this.router.navigate([
            '/partida',
            this.partidaId,
            'ciudad',
            this.ciudadId,
            'caravana',
            this.caravanaId,
            'fin'
          ]);
        }
      });
    });
  }
  

  confirmarViaje(item: any): void {
    const tiempoEstimado = item.ruta.distancia / this.caravana.velocidadActual;
    let mensaje = `Tardarás aproximadamente ${tiempoEstimado.toFixed(2)} minutos.<br>`;
    mensaje += item.ruta.es_segura
      ? "La ruta es segura.<br>"
      : "La ruta no es segura. Perderás 10 puntos de vida si viajas por ella.<br>";
    mensaje += `El coste de entrada a la ciudad es de ${item.ciudad.impuesto_entrada} monedas.<br>`;
    mensaje += `¿Quieres viajar a ${item.ciudad.nombre}?`;
  
    this.ciudadSeleccionadaParaViajar = item;
    this.mensajeConfirmacion = mensaje;
    this.mostrarPopup = true;
  }
  
  viajarAConfirmado(): void {
    const item = this.ciudadSeleccionadaParaViajar;
    const ciudadId = item.ciudad.id;
  
    this.ciudadService.recuperarCiudad(ciudadId).subscribe(ciudad => {
      this.ciudadEscogida = ciudad;
      if (!this.ciudadEscogida) {
        alert("Error al recuperar la ciudad.");
        return;
      }
  
      if (this.caravana.dinero.valueOf() < this.ciudadEscogida.impuesto_entrada.valueOf()) {
        alert("No tienes suficiente dinero para viajar a esta ciudad");
        return;
      }
  
      this.caravana.dinero -= this.ciudadEscogida.impuesto_entrada.valueOf();
      this.caravana.saludActual -= item.ruta.es_segura ? 0 : 10;
      this.caravana.tiempoAcumulado += item.ruta.distancia / this.caravana.velocidadActual;
      this.caravanaService.actualizarCaravana(this.caravana).subscribe(() => {
        console.log("Caravana actualizada:", this.caravana);
      });
  
      this.ciudadCaravanaService.recuperarCaravanaCiudad(ciudadId).subscribe(caravanaCiudad => {
        caravanaCiudad.caravanasIds.push(this.caravanaId);
        this.ciudadCaravanaService.actualizarCaravanaCiudad(ciudadId, caravanaCiudad).subscribe(() => {
          console.log("Caravana ciudad actualizada:", caravanaCiudad);
        });
      });
  
      this.partidaCaravanaService.recuperarPartidaCaravana(this.partidaId).subscribe(partidaCaravana => {
        partidaCaravana.caravanasIds.push(this.caravanaId);
        this.partidaCaravanaService.actualizarPartidaCaravana(this.partidaId, partidaCaravana).subscribe(() => {
          console.log("Partida caravana actualizada:", partidaCaravana);
        });
      });

      const tiempoRestante = this.partida.tiempoLimite - this.caravana.tiempoAcumulado;
      this.contadorService.iniciarContador(tiempoRestante);


      if (this.caravana.saludActual > 0){
        this.router.navigate([
          '/partida',
          this.partidaId,
          'ciudad',
          ciudadId,
          'caravana',
          this.caravanaId
        ]);
      }
      else {
        if (this.caravana.saludActual <= 0) {
          alert("Tu caravana ha muerto en el viaje.");
        }
        else if (this.caravana.tiempoAcumulado > this.partida.tiempoLimite) {
          alert("La partida ha finaizado.");
        }
        this.router.navigate([
          '/partida',
          this.partidaId,
          'ciudad',
          ciudadId,
          'caravana',
          this.caravanaId,
          'fin'
        ]);
      }
    });
  
    this.mostrarPopup = false;
  }  
}  

interface CiudadConRuta {
  ciudad: CiudadDto;
  ruta: RutaDto;
}
