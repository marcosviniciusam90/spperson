import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Pessoa } from './pessoa.model';
import { environment } from 'src/environments/environment';

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
    params = params.set('size', filtro.itensPorPagina.toString());

    // parâmetros do filtro de pesquisa
    if (filtro.nome) {
      params = params.set('nome', filtro.nome);
    }

    return this.http.get(`${this.pessoasUrl}`, { params })
      .toPromise();
  }

  excluir(codigo: number): Promise<any> {
    return this.http.delete(`${this.pessoasUrl}/${codigo}`)
      .toPromise();
  }

  alterarStatus(codigo: number, ativo: boolean): Promise<any> {
    return this.http.put(`${this.pessoasUrl}/${codigo}/ativo`, ativo)
      .toPromise();
  }

  adicionar(pessoa: Pessoa): Promise<Pessoa> {
    return this.http.post<Pessoa>(this.pessoasUrl, pessoa)
      .toPromise();
  }

  atualizar(pessoa: Pessoa): Promise<Pessoa> {
    return this.http.put<Pessoa>(`${this.pessoasUrl}/${pessoa.codigo}`, pessoa)
      .toPromise();
  }

  buscarPorCodigo(codigo: number): Promise<Pessoa> {
    return this.http.get<Pessoa>(`${this.pessoasUrl}/${codigo}`)
      .toPromise();
  }
}
