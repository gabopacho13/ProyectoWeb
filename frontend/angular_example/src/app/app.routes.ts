import { Routes } from '@angular/router';
import { CiudadListaComponent } from './ciudad/ciudad-lista/ciudad-lista.component';
import { CiudadViewComponent } from './ciudad/ciudad-view/ciudad-view.component';
import { CiudadEditComponent } from './ciudad/ciudad-edit/ciudad-edit.component';
import { PartidaInicioComponent } from './partida/partida-inicio/partida-inicio.component';
import { CiudadViajeComponent } from './ciudad/ciudad-ruta/ciudad-ruta.component';
import { ProductoComercioComponent } from './producto/producto-comercio/producto-comercio.component';
import { ProductoDetalleComponent } from './producto/producto-detalle/producto-detalle.component';

export const routes: Routes = [
    { path: 'ciudad/lista', component: CiudadListaComponent },
    { path: 'ciudad/view/:id', component: CiudadViewComponent },
    { path: 'ciudad/edit/:id', component: CiudadEditComponent },
    { path: 'ciudad/rutas/:id', component: CiudadViajeComponent },
    { path: 'ciudad/producto/:id', component: ProductoComercioComponent},
    { path: 'producto/:id', component: ProductoDetalleComponent },
    { path: 'inicio', component: PartidaInicioComponent },
    { path: '', pathMatch: 'full', redirectTo: 'inicio' },
];
