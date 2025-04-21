import { Routes } from '@angular/router';
import { CiudadListaComponent } from './ciudad/ciudad-lista/ciudad-lista.component';
import { CiudadViewComponent } from './ciudad/ciudad-view/ciudad-view.component';
import { CiudadEditComponent } from './ciudad/ciudad-edit/ciudad-edit.component';
import { PartidaInicioComponent } from './partida/partida-inicio/partida-inicio.component';
import { CiudadViajeComponent } from './ciudad/ciudad-ruta/ciudad-ruta.component';
import { ProductoComercioComponent } from './producto/producto-comercio/producto-comercio.component';
import { ProductoDetalleComponent } from './producto/producto-detalle/producto-detalle.component';
import{ CiudadActualComponent } from './ciudad/ciudad-actual/ciudad-actual.component';
import { ViajarComponent } from './ciudad/viajar/viajar.component';
import { ServicioComercioComponent } from './servicio/servicio-comercio/servicio-comercio.component';
import { ServicioViewComponent } from './servicio/servicio-view/servicio-view.component';

export const routes: Routes = [
    { path: 'ciudad/lista', component: CiudadListaComponent },
    { path: 'ciudad/view/:id', component: CiudadViewComponent },
    { path: 'ciudad/edit/:id', component: CiudadEditComponent },
    { path: 'ciudad/rutas/:id', component: CiudadViajeComponent },
    { path: 'producto/ciudad/:id', component: ProductoComercioComponent},
    { path: 'producto/:id', component: ProductoDetalleComponent },
    { path: 'inicio', component: PartidaInicioComponent },
    { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana', component: CiudadActualComponent } ,  
    { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana/viajar', component: ViajarComponent },
    { path: 'servicio/:id', component: ServicioViewComponent },
    { path: 'servicio/ciudad/:id', component: ServicioComercioComponent },
    { path: '', pathMatch: 'full', redirectTo: 'inicio' }
];
