import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';

import { AuthGuard } from './seguranca/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'pessoas', pathMatch: 'full' },

  {
    path: 'pagina-nao-encontrada',
    component: PaginaNaoEncontradaComponent,
    canActivate: [AuthGuard]
  },

  { path: '**', redirectTo: 'pagina-nao-encontrada' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
