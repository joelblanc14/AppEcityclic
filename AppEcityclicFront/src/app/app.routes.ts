import { Routes } from '@angular/router';

export const routes: Routes = [

  {path: 'empresa',
    loadComponent: () => import('./articles/empresa/empresa-list/empresa-list.component').then(m => m.EmpresaListComponent)
  },
  {path: 'empresa/create',
    loadComponent: () => import('./articles/empresa/empresa-create/empresa-create.component').then(m => m.EmpresaCreateComponent)
  },
  {path: 'empresa/edit/:id',
    loadComponent: () => import('./articles/empresa/empresa-update/empresa-update.component').then(m => m.EmpresaUpdateComponent)
  },
  {path: 'empresa/:empresaId/treballadors',
    loadComponent: () => import('./articles/treballador/treballador-list/treballador-list.component').then(m => m.TreballadorListComponent)
  },
  {path: 'empresa/:empresaId/treballadors/create',
    loadComponent: () => import('./articles/treballador/treballador-create/treballador-create.component').then(m => m.TreballadorCreateComponent)
  },
  {path: 'empresa/:empresaId/treballadors/edit/:treballadorId',
    loadComponent: () => import('./articles/treballador/treballador-update/treballador-update.component').then(m => m.TreballadorUpdateComponent)
  },
  {path: 'empresa/:empresaId/projecte',
    loadComponent: () => import('./articles/projecte/projecte-list/projecte-list.component').then(m => m.ProjecteListComponent)
  },
  {path: 'empresa/:empresaId/projecte/create',
    loadComponent: () => import('./articles/projecte/projecte-create/projecte-create.component').then(m => m.ProjecteCreateComponent)
  },
  {path: 'empresa/:empresaId/projecte/edit/:projecteId',
    loadComponent: () => import('./articles/projecte/projecte-update/projecte-update.component').then(m => m.ProjecteUpdateComponent)
  },
  {path: 'empresa/:empresaId/treballador/:treballadorId/tasques',
    loadComponent: () => import('./articles/tasca/tasca-list-treballador/tasca-list-treballador.component').then(m => m.TascaListTreballadorComponent)
   },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques',
    loadComponent: () => import('./articles/tasca/tasca-list-projecte/tasca-list-projecte.component').then(m => m.TascaListProjecteComponent)
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/create',
    loadComponent: () => import('./articles/tasca/tasca-create/tasca-create.component').then(m => m.TascaCreateComponent)
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/tasques/edit/:tascaId',
    loadComponent: () => import('./articles/tasca/tasca-update/tasca-update.component').then(m => m.TascaUpdateComponent)
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/client',
    loadComponent: () => import('./articles/client-folder/client-list/client-list.component').then(m => m.ClientListComponent)
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/client/create',
    loadComponent: () => import('./articles/client-folder/client-create/client-create.component').then(m => m.ClientCreateComponent)
  },
  {path: 'empresa/:empresaId/projecte/:projecteId/client/edit/:clientId',
    loadComponent: () => import('./articles/client-folder/client-update/client-update.component').then(m => m.ClientUpdateComponent)
  },
  {path: '**', redirectTo: 'empresa', pathMatch: 'full'},
];
