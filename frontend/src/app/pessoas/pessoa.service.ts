import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Pessoa } from './pessoa.model';
import { environment } from 'src/environments/environment';
import * as moment from 'moment';

export class PessoaFiltro {
  nome: string;
  pagina: number;
  itensPorPagina = 5;
}

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  pessoasUrl = `${environment.apiUrl}/pessoas`;

  constructor(
    private http: HttpClient
  ) { }

  listarTodas(): Promise<any> {

    return this.http.get(`${this.pessoasUrl}`)
      .toPromise();
  }

  pesquisar(filtro: PessoaFiltro): Promise<any> {
    let params = new HttpParams();

    // parâmetros de paginação
    params = params.set('page', filtro.pagina.toString());
    params = params.set('linesPerPage', filtro.itensPorPagina.toString());

    // parâmetros do filtro de pesquisa
    if (filtro.nome) {
      params = params.set('nome', filtro.nome);
    }

    return this.http.get(`${this.pessoasUrl}`, { params })
      .toPromise();
  }

  excluir(id: number): Promise<any> {
    return this.http.delete(`${this.pessoasUrl}/${id}`)
      .toPromise();
  }

  adicionar(pessoa: Pessoa): Promise<Pessoa> {
    return this.http.post<Pessoa>(this.pessoasUrl, pessoa)
      .toPromise();
  }

  atualizar(pessoa: Pessoa): Promise<Pessoa> {
    return this.http.put<Pessoa>(`${this.pessoasUrl}/${pessoa.id}`, pessoa)
      .toPromise()
      .then(response => {
        const pessoaAlterada = response;

        this.converterStringsParaDatas([pessoaAlterada]);

        return pessoaAlterada;
      });
  }

  buscarPorId(id: number): Promise<Pessoa> {
    return this.http.get<Pessoa>(`${this.pessoasUrl}/${id}`)
      .toPromise()
      .then(response => {
        const pessoa = response;

        this.converterStringsParaDatas([pessoa]);

        return pessoa;
      });
  }

  private converterStringsParaDatas(pessoas: Pessoa[]): void {
    for (const pessoa of pessoas) {
      pessoa.dataNascimento = moment(pessoa.dataNascimento,
        'YYYY-MM-DD').toDate();
    }
  }
}
