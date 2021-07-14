import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';
import { Table } from 'primeng/table';

import { AuthService } from 'src/app/seguranca/auth.service';
import { Pessoa } from '../pessoa.model';

@Component({
  selector: 'app-pessoas-grid',
  templateUrl: './pessoas-grid.component.html',
  styleUrls: ['./pessoas-grid.component.css']
})
export class PessoasGridComponent {

    @Input() pessoas: Pessoa[];

    @Input() itensPorPagina: number;
    @Input() totalRegistros: number;

    @Output() paginaAlterada = new EventEmitter();
    @Output() pessoaExcluida = new EventEmitter();

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

    confirmarExclusao(pessoa: Pessoa): void {
      this.confirmationService.confirm({
        header: `Excluir: ${pessoa.nome}`,
        message: 'Tem certeza que deseja excluir?',
        accept: () => this.excluir(pessoa.id)
        // reject
      });

    }

    excluir(pessoaId: number): void {
      this.pessoaExcluida.emit({ pessoaId, paginaAtual: this.paginaAtual });
    }

}
