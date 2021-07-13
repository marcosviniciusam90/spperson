import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { Table } from 'primeng/table';

import { AuthService } from 'src/app/seguranca/auth.service';
import { LancamentoResumo } from '../lancamento.model';

@Component({
  selector: 'app-lancamentos-grid',
  templateUrl: './lancamentos-grid.component.html',
  styleUrls: ['./lancamentos-grid.component.css']
})
export class LancamentosGridComponent {

    @Input() lancamentos: LancamentoResumo[];

    @Input() itensPorPagina: number;
    @Input() totalRegistros: number;

    @Output() paginaAlterada = new EventEmitter();
    @Output() lancamentoExcluido = new EventEmitter();

    @ViewChild('tabela') grid: Table;

    paginaAtual = 0;

    constructor(
      private confirmationService: ConfirmationService,
      public authService: AuthService
    ) {}

    setPage(pagina: number): void {
      this.grid.first = pagina * this.grid.rows;
    }

    aoMudarPagina(event: LazyLoadEvent): void {
      this.paginaAtual = event.first / event.rows;
      this.paginaAlterada.emit(this.paginaAtual);
    }

    confirmarExclusao(lancamento: LancamentoResumo): void {
      this.confirmationService.confirm({
        header: `Excluir: ${lancamento.descricao}`,
        message: 'Tem certeza que deseja excluir?',
        accept: () => this.excluir(lancamento.codigo)
        // reject
      });

    }

    excluir(lancamentoCodigo: number): void {
      this.lancamentoExcluido.emit({ lancamentoCodigo, paginaAtual: this.paginaAtual });
    }

}
