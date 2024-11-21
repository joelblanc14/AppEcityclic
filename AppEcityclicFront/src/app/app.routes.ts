import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  {path: 'login',
    loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
  },
  {path: 'empresa',
    loadComponent: () => import('./articles/empresa/empresa-list/empresa-list.component').then(m => m.EmpresaListComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/create',
    loadComponent: () => import('./articles/empresa/empresa-create/empresa-create.component').then(m => m.EmpresaCreateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/edit/:id',
    loadComponent: () => import('./articles/empresa/empresa-update/empresa-update.component').then(m => m.EmpresaUpdateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/treballadors',
      loadComponent: () => import('./articles/treballador/treballador-list/treballador-list.component').then(m => m.TreballadorListComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/treballadors/create',
    loadComponent: () => import('./articles/treballador/treballador-create/treballador-create.component').then(m => m.TreballadorCreateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/treballadors/edit/:treballadorId',
    loadComponent: () => import('./articles/treballador/treballador-update/treballador-update.component').then(m => m.TreballadorUpdateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/projecte',
    loadComponent: () => import('./articles/projecte/projecte-list/projecte-list.component').then(m => m.ProjecteListComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/projecte/create',
    loadComponent: () => import('./articles/projecte/projecte-create/projecte-create.component').then(m => m.ProjecteCreateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/projecte/edit/:projecteId',
    loadComponent: () => import('./articles/projecte/projecte-update/projecte-update.component').then(m => m.ProjecteUpdateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/treballador/:treballadorId/tasques',
    loadComponent: () => import('./articles/tasca/tasca-list-treballador/tasca-list-treballador.component').then(m => m.TascaListTreballadorComponent),
    canActivate: [authGuard]
   },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques',
    loadComponent: () => import('./articles/tasca/tasca-list-projecte/tasca-list-projecte.component').then(m => m.TascaListProjecteComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/create',
    loadComponent: () => import('./articles/tasca/tasca-create/tasca-create.component').then(m => m.TascaCreateComponent),
    canActivate: [authGuard]
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/edit/:tascaId',
    loadComponent: () => import('./articles/tasca/tasca-update/tasca-update.component').then(m => m.TascaUpdateComponent)
  },
  {path: 'projecte/:projecteId/client',
    loadComponent: () => import('./articles/client-folder/client-list/client-list.component').then(m => m.ClientListComponent),
    canActivate: [authGuard]
  },
  {path: 'projecte/:projecteId/client/create',
    loadComponent: () => import('./articles/client-folder/client-create/client-create.component').then(m => m.ClientCreateComponent),
    canActivate: [authGuard]
  },
  {path: 'projecte/:projecteId/client/edit/:clientId',
    loadComponent: () => import('./articles/client-folder/client-update/client-update.component').then(m => m.ClientUpdateComponent),
    canActivate: [authGuard]
  },
  {path: '**', redirectTo: 'login', pathMatch: 'full'},
];
