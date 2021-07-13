export class Pessoa {
  codigo: number;
  nome: string;
  cpf: string;
  ativo = true;
  endereco = new Endereco();
}

class Endereco {
  logradouro: string;
  numero: string;
  complemento: string;
  bairro: string;
  cep: string;
  cidade: string;
  estado: string;
}

export class PessoaResumo {
  codigo: number;
  nome: string;
}
