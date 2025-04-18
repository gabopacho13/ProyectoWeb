import { Routes } from '@angular/router';
import { CiudadListaComponent } from './ciudad/ciudad-lista/ciudad-lista.component';
import { CiudadViewComponent } from './ciudad/ciudad-view/ciudad-view.component';
import { CiudadEditComponent } from './ciudad/ciudad-edit/ciudad-edit.component';

export const routes: Routes = [
    { path: 'ciudad/lista', component: CiudadListaComponent },
    { path: 'ciudad/view/:id', component: CiudadViewComponent },
    { path: 'ciudad/edit/:id', component: CiudadEditComponent },
    
];
