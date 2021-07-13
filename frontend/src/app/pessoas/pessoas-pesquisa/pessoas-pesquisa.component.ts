import { Component, OnInit, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { MessageService } from 'primeng/api';

import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { Pessoa } from '../pessoa.model';
import { PessoaFiltro, PessoaService } from '../pessoa.service';
import { PessoasGridComponent } from '../pessoas-grid/pessoas-grid.component';

@Component({
  selector: 'app-pessoas-pesquisa',
  templateUrl: './pessoas-pesquisa.component.html',
  styleUrls: ['./pessoas-pesquisa.component.css']
})
export class PessoasPesquisaComponent implements OnInit {

    pessoas: Pessoa[];

    filtro = new PessoaFiltro();
    totalRegistros = 0;

    @ViewChild(PessoasGridComponent) gridComponent: PessoasGridComponent;

    constructor(
      private pessoaService: PessoaService,
      private messageService: MessageService,
      private errorHandlerService: ErrorHandlerService,
      private title: Title
    ) {}

    ngOnInit(): void {
      this.title.setTitle('Pesquisa de pessoas');
    }

    pesquisar(pagina = 0): void {
      this.filtro.pagina = pagina;

      this.pessoaService.pesquisar(this.filtro)
        .then(response => {
          this.pessoas = response.content;
          this.totalRegistros = response.totalElements;

          this.gridComponent.setPage(pagina);
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }

    excluir(exclusao: any): void {
      this.pessoaService.excluir(exclusao.pessoaCodigo)
        .then(() => {
          this.pesquisar(exclusao.paginaAtual);
          this.messageService.add({ severity: 'success', detail: 'Pessoa excluída com sucesso!' });
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }

    alterarStatus(pessoa: Pessoa): void {
      const novoStatus = !pessoa.ativo;
      this.pessoaService.alterarStatus(pessoa.codigo, novoStatus)
        .then(() => {
          pessoa.ativo = novoStatus;

          this.messageService.add({
            severity: 'success',
            detail: `Pessoa ${novoStatus ? 'ativada' : 'desativada'} com sucesso!`
          });
        })
        .catch(erro => this.errorHandlerService.handle(erro));
    }

}
