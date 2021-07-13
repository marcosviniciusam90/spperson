import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ToastModule} from 'primeng/toast';
import { MessageService } from 'primeng/api';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import { RouterModule } from '@angular/router';
import { Title } from '@angular/platform-browser';
import {ProgressSpinnerModule} from 'primeng/progressspinner';

import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

import { NavbarComponent } from './navbar/navbar.component';
import { LancamentoService } from '../lancamentos/lancamento.service';
import { PessoaService } from '../pessoas/pessoa.service';
import { ErrorHandlerService } from './error-handler.service';
import { CategoriaService } from '../categorias/categoria.service';
import { PaginaNaoEncontradaComponent } from './pagina-nao-encontrada.component';
import { AuthService } from '../seguranca/auth.service';
import { NaoAutorizadoComponent } from './nao-autorizado.component';
import { LoadingComponent } from './loading/loading.component';
import { LoadingService } from './loading/loading.service';

registerLocaleData(localePt);

@NgModule({
  declarations: [
      NavbarComponent,
      PaginaNaoEncontradaComponent,
      NaoAutorizadoComponent,
      LoadingComponent
  ],
  imports: [
    CommonModule,
    ToastModule,
    ConfirmDialogModule,
    RouterModule,
    ProgressSpinnerModule
  ],
  exports: [
      NavbarComponent,
      ToastModule,
      ConfirmDialogModule,
      LoadingComponent
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' },

    MessageService,
    ConfirmationService,
    Title,

    LancamentoService,
    PessoaService,
    ErrorHandlerService,
    CategoriaService,
    AuthService,
    LoadingService
  ]
})
export class CoreModule { }
