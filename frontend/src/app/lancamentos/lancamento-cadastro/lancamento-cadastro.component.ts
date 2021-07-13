import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Categoria } from 'src/app/categorias/categoria.model';
import { CategoriaService } from 'src/app/categorias/categoria.service';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { PessoaResumo } from 'src/app/pessoas/pessoa.model';
import { PessoaService } from 'src/app/pessoas/pessoa.service';
import { Lancamento } from '../lancamento.model';
import { LancamentoService } from '../lancamento.service';

@Component({
  selector: 'app-lancamento-cadastro',
  templateUrl: './lancamento-cadastro.component.html',
  styleUrls: ['./lancamento-cadastro.component.css']
})
export class LancamentoCadastroComponent implements OnInit{

    tipos = [
        { label: 'Receita', value: 'RECEITA'},
        { label: 'Despesa', value: 'DESPESA'}
    ];

    categorias: any[];
    pessoas: any[];
    lancamento = new Lancamento();

    constructor(
      private categoriaService: CategoriaService,
      private pessoaService: PessoaService,
      private lancamentoService: LancamentoService,
      private errorHandlerService: ErrorHandlerService,
      private messageService: MessageService,
      private route: ActivatedRoute,
      private router: Router,
      private title: Title
    ) {}

    ngOnInit(): void {
      this.title.setTitle('Novo Lançamento');
      const codigoLancamento = this.route.snapshot.params.codigo;
      if (codigoLancamento) {
        this.carregarLancamento(codigoLancamento);
      }

      this.carregarCategorias();
      this.carregarPessoas();
    }

    get editando(): boolean {
      return Boolean(this.lancamento.codigo);
    }

    atualizarTituloEdicao(): void {
      this.title.setTitle(`Alterando Lançamento: ${this.lancamento.descricao}`);
    }

    carregarLancamento(codigo: number): void {
      this.lancamentoService.buscarPorCodigo(codigo)
        .then(lancamento => {
          this.lancamento = lancamento;
          this.atualizarTituloEdicao();
        })
        .catch(erro => this.errorHandlerService.handle(erro));

    }

    carregarCategorias(): void {
      this.categoriaService.listarTodas()
      .then(response => {
        const categorias = response as Categoria[];
        this.categorias = categorias.map(cat => ({ label: cat.nome, value: cat.codigo }));
      })
      .catch(erro => this.errorHandlerService.handle(erro));
    }

    carregarPessoas(): void {
      this.pessoaService.listarTodas()
      .then(response => {
        const pessoas = response.content as PessoaResumo[];
        this.pessoas = pessoas.map(pes => ({ label: pes.nome, value: pes.codigo }));
      })
      .catch(erro => this.errorHandlerService.handle(erro));
    }

    salvar(): void {
      if (this.editando) {
        this.atualizarLancamento();
      } else {
        this.adicionarLancamento();
      }
    }

    adicionarLancamento(): void {
      this.lancamentoService.adicionar(this.lancamento)
      .then(lancamentoAdicionado => {
        this.messageService.add({ severity: 'success', detail: 'Lançamento adicionado com sucesso!' });

        this.router.navigate(['/lancamentos', lancamentoAdicionado.codigo]);
      })
      .catch(erro => this.errorHandlerService.handle(erro));
    }

    atualizarLancamento(): void {
      this.lancamentoService.atualizar(this.lancamento)
      .then(lancamento => {
        this.lancamento = lancamento;

        this.messageService.add({ severity: 'success', detail: 'Lançamento alterado com sucesso!' });
        this.atualizarTituloEdicao();
      })
      .catch(erro => this.errorHandlerService.handle(erro));
    }

    novo(form: NgForm): void {
      form.reset(new Lancamento());

      this.router.navigate(['/lancamentos/novo']);
    }


}
