import { Routes } from '@angular/router';
import { EmpresaListComponent } from './articles/empresa/empresa-list/empresa-list.component';
import { TreballadorListComponent } from './articles/treballador/treballador-list/treballador-list.component';
import { ProjecteListComponent } from './articles/projecte/projecte-list/projecte-list.component';
import { ClientListComponent } from './articles/client-folder/client-list/client-list.component';
import { EmpresaCreateComponent } from './articles/empresa/empresa-create/empresa-create.component';
import { EmpresaUpdateComponent } from './articles/empresa/empresa-update/empresa-update.component';
import { TreballadorCreateComponent } from './articles/treballador/treballador-create/treballador-create.component';
import { TreballadorUpdateComponent } from './articles/treballador/treballador-update/treballador-update.component';
import { ProjecteCreateComponent } from './articles/projecte/projecte-create/projecte-create.component';
import { ProjecteUpdateComponent } from './articles/projecte/projecte-update/projecte-update.component';
import { TascaListTreballadorComponent } from './articles/tasca/tasca-list-treballador/tasca-list-treballador.component';
import { TascaListProjecteComponent } from './articles/tasca/tasca-list-projecte/tasca-list-projecte.component';
import { TascaCreateComponent } from './articles/tasca/tasca-create/tasca-create.component';
import { TascaUpdateComponent } from './articles/tasca/tasca-update/tasca-update.component';
import { ClientCreateComponent } from './articles/client-folder/client-create/client-create.component';
import { ClientUpdateComponent } from './articles/client-folder/client-update/client-update.component';

export const routes: Routes = [

  {path: 'empreses', component:EmpresaListComponent},
  {path: 'empresa/create', component: EmpresaCreateComponent },
  {path: 'empresa/update/:id', component: EmpresaUpdateComponent },
  {path: 'empresa/:empresaId/treballadors', component: TreballadorListComponent },
  {path: 'empresa/:empresaId/treballadors/create', component: TreballadorCreateComponent},
  {path: 'empresa/:empresaId/treballadors/update/:treballadorId', component: TreballadorUpdateComponent},
  {path: 'empresa/:empresaId/projecte', component:ProjecteListComponent},
  {path: 'empresa/:empresaId/projecte/create', component:ProjecteCreateComponent},
  {path: 'empresa/:empresaId/projecte/update/:projecteId', component:ProjecteUpdateComponent},
  {path: 'empresa/:empresaId/treballador/:treballadorId/tasques', component: TascaListTreballadorComponent },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques', component: TascaListProjecteComponent},
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/create', component:TascaCreateComponent},
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/update/:tascaId', component:TascaUpdateComponent},
  {path: 'empresa/:empresaId/projecte/:projecteId/client', component:ClientListComponent},
  {path: 'empresa/:empresaId/projecte/:projecteId/client/create', component:ClientCreateComponent},
  {path: 'empresa/:empresaId/projecte/:projecteId/client/update/:clientId', component:ClientUpdateComponent},
  {path: '**', redirectTo: 'empreses', pathMatch: 'full'},
];
