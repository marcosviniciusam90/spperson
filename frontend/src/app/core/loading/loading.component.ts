import { Component } from '@angular/core';
import { LoadingService } from './loading.service';

@Component({
  selector: 'app-loading',
  template: `
    <div *ngIf="loadingService.exibirProgressSpinner" class="loading">
      <p-progressSpinner [style]="{width: '100px', height: '100px'}"></p-progressSpinner>
    </div>
  `,
  styles: [
    `
      .loading {
        position: fixed; /* se estiver scroll o menu fica fixo */
        z-index: 9992; /* número alto, para ter prioridade e ficar por cima de todos os elementos */
        margin-top: -50px; /* tira diferença da metade da altura do componente */
        margin-left: -50px; /* tira diferença da metade da largura do componente */
        top: 50%;
        left: 50%;
      }
    `
  ]
})
export class LoadingComponent {

  constructor(public loadingService: LoadingService) {}

}
