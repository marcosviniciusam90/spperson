import { Component, OnInit, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { MessageService } from 'primeng/api';

import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { LancamentoResumo } from '../lancamento.model';
import { LancamentoFiltro, LancamentoService } from '../lancamento.service';
import { LancamentosGridComponent } from '../lancamentos-grid/lancamentos-grid.component';

@Component({
  selector: 'app-lancamentos-pesquisa',
  templateUrl: './lancamentos-pesquisa.component.html',
  styleUrls: ['./lancamentos-pesquisa.component.css']
})
export class LancamentosPesquisaComponent implements OnInit {

    lancamentos: LancamentoResumo[];

    filtro = new LancamentoFiltro();
    totalRegistros = 0;

    @ViewChild(LancamentosGridComponent) gridComponent: LancamentosGridComponent;

    constructor(
      private lancamentoService: LancamentoService,
      private messageService: MessageService,
      private errorHandlerService: ErrorHandlerService,
      private title: Title
    ) {}

    ngOnInit(): void {
      this.title.setTitle('Pesquisa de lançamentos');
    }

    pesquisar(pagina = 0): void {
      this.filtro.pagina = pagina;

      this.lancamentoService.pesquisar(this.filtro)
        .then(response => {
          this.lancamentos = response.content;
          this.totalRegistros = response.totalElements;

          this.gridComponent.setPage(pagina);
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }

    excluir(exclusao: any): void {
      this.lancamentoService.excluir(exclusao.lancamentoCodigo)
        .then(() => {
          this.pesquisar(exclusao.paginaAtual);
          this.messageService.add({ severity: 'success', detail: 'Lançamento excluído com sucesso!' });
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }
}
