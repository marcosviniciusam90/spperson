import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

import { NotAuthenticatedError } from '../seguranca/app-http-interceptor';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService {

  constructor(
    private messageService: MessageService,
    private router: Router
  ) { }

  handle(errorResponse: any): void {
    let msg: string;

    if (typeof errorResponse === 'string') {
      msg = errorResponse;

    } else if (errorResponse instanceof NotAuthenticatedError) {
      msg = 'Sua sessão expirou!';
      this.router.navigate(['/login']);

    } else if (errorResponse instanceof HttpErrorResponse
      && errorResponse.status >= 400 && errorResponse.status <= 499) {
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      if (errorResponse.status === 403) {
        msg = 'Você não tem permissão para executar esta ação';
      }

      try {
        msg = errorResponse.error.erro;

        if(errorResponse.error.campos) {
          msg = errorResponse.error.campos[0].mensagem;
        }
      } catch (e) { }

    } else {
      msg = 'Erro ao processar serviço remoto. Tente novamente!';
    }

    console.log('Ocorreu um erro ->', errorResponse);
    this.messageService.add({ severity: 'error', detail: msg });
  }
}
