import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ErrorHandlerService } from 'src/app/core/error-handler.service';
import { Pessoa } from '../pessoa.model';
import { PessoaService } from '../pessoa.service';

@Component({
  selector: 'app-pessoa-cadastro',
  templateUrl: './pessoa-cadastro.component.html',
  styleUrls: ['./pessoa-cadastro.component.css']
})
export class PessoaCadastroComponent implements OnInit {

  sexo = [
    { label: 'Masculino', value: 'MASCULINO'},
    { label: 'Feminino', value: 'FEMININO'}
  ];

  pessoa = new Pessoa();

  constructor(
    private pessoaService: PessoaService,
    private messageService: MessageService,
    private errorHandlerService: ErrorHandlerService,
    private route: ActivatedRoute,
    private router: Router,
    private title: Title
  ) {}

  ngOnInit(): void {
    this.title.setTitle('Nova Pessoa');
    const pessoaId = this.route.snapshot.params.id;
    if (pessoaId) {
      this.carregarPessoa(pessoaId);
    }
  }

  get editando(): boolean {
    return Boolean(this.pessoa.id);
  }

  atualizarTituloEdicao(): void {
    this.title.setTitle(`Alterando Pessoa: ${this.pessoa.nome}`);
  }

  carregarPessoa(id: number): void {
    this.pessoaService.buscarPorId(id)
      .then(pessoa => {
        this.pessoa = pessoa;
        this.atualizarTituloEdicao();
      })
      .catch(erro => this.errorHandlerService.handle(erro));

  }

  salvar(): void {
    if (this.editando) {
      this.atualizarPessoa();
    } else {
      this.adicionarPessoa();
    }
  }

  adicionarPessoa(): void {
    this.pessoaService.adicionar(this.pessoa)
    .then(pessoaAdicionada => {
      this.messageService.add({ severity: 'success', detail: 'Pessoa adicionada com sucesso!' });

      this.router.navigate(['/pessoas', pessoaAdicionada.id]);
    })
    .catch(erro => this.errorHandlerService.handle(erro));
  }

  atualizarPessoa(): void {
    this.pessoaService.atualizar(this.pessoa)
    .then(pessoa => {
      this.pessoa = pessoa;

      this.messageService.add({ severity: 'success', detail: 'Pessoa alterada com sucesso!' });
      this.atualizarTituloEdicao();
    })
    .catch(erro => this.errorHandlerService.handle(erro));
  }

  novo(form: NgForm): void {
    form.reset(new Pessoa());

    this.router.navigate(['/pessoas/nova']);
  }

}
