import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  exibirProgressSpinner = false;

  exibir(status: boolean): void {
    this.exibirProgressSpinner = status;
  }

}
