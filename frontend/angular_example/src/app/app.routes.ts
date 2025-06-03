import { Routes } from '@angular/router';
import { CiudadListaComponent } from './ciudad/ciudad-lista/ciudad-lista.component';
import { CiudadViewComponent } from './ciudad/ciudad-view/ciudad-view.component';
import { CiudadEditComponent } from './ciudad/ciudad-edit/ciudad-edit.component';
import { PartidaInicioComponent } from './partida/partida-inicio/partida-inicio.component';
import { CiudadViajeComponent } from './ciudad/ciudad-ruta/ciudad-ruta.component';
import { ProductoComercioComponent } from './producto/producto-comercio/producto-comercio.component';
import { ProductoDetalleComponent } from './producto/producto-detalle/producto-detalle.component';
import { CiudadActualComponent } from './ciudad/ciudad-actual/ciudad-actual.component';
import { ViajarComponent } from './ciudad/viajar/viajar.component';
import { ServicioComercioComponent } from './servicio/servicio-comercio/servicio-comercio.component';
import { ServicioViewComponent } from './servicio/servicio-view/servicio-view.component';
import { FinPartidaComponent } from './partida/fin-partida/fin-partida.component';

import { LoginComponent } from './seguridad-frontend/login/login.component';
import { AuthGuard } from './seguridad-frontend/guard/auth.guard';
import { AdminComponent } from './admin/admin.component';
import { CaravanaComponent } from './caravana/caravana/caravana.component';
import { RegisterComponent } from './seguridad-frontend/register/register.component';

export const routes: Routes = [
  { path: 'ciudad/lista', component: CiudadListaComponent },
  { path: 'ciudad/view/:id', component: CiudadViewComponent },
  { path: 'ciudad/edit/:id', component: CiudadEditComponent },
  { path: 'ciudad/rutas/:id', component: CiudadViajeComponent },
  { path: 'producto/ciudad/:id', component: ProductoComercioComponent },
  { path: 'producto/:id', component: ProductoDetalleComponent },
  { path: 'inicio', component: PartidaInicioComponent },
  { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana', component: CiudadActualComponent },
  { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana/viajar', component: ViajarComponent },
  { path: 'servicio/:id', component: ServicioViewComponent },
  { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana/fin', component: FinPartidaComponent },
  { path: 'partida/:idPartida/ciudad/:idCiudad/caravana/:idCaravana/servicios', component: ServicioComercioComponent },
  { path: 'caravana/:idCaravana/ciudad/:idCiudad/comercio', component: ProductoComercioComponent },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'producto', component: ProductoComercioComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: 'caravana', component: CaravanaComponent, canActivate: [AuthGuard] },

  { path: '', pathMatch: 'full', redirectTo: 'inicio' },
  { path: '**', redirectTo: 'login' }
];