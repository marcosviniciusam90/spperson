import { Categoria } from '../categorias/categoria.model';
import { PessoaResumo } from '../pessoas/pessoa.model';

export class Lancamento {
  codigo: number;
  descricao: string;
  dataVencimento: Date;
  dataPagamento: Date;
  valor: number;
  observacao: string;
  tipo = 'RECEITA';
  categoria = new Categoria();
  pessoa = new PessoaResumo();
}

export class LancamentoResumo {
  codigo: number;
  descricao: string;
  dataVencimento: Date;
  dataPagamento: Date;
  valor: number;
  observacao: string;
  tipo: string;
  categoria: string;
  pessoa: string;
}
